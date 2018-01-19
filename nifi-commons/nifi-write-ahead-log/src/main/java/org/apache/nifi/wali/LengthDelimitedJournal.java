/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.wali;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.nifi.stream.io.ByteCountingInputStream;
import org.apache.nifi.stream.io.LimitingInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wali.SerDe;
import org.wali.SerDeFactory;

public class LengthDelimitedJournal<T> implements WriteAheadJournal<T> {
    private static final Logger logger = LoggerFactory.getLogger(LengthDelimitedJournal.class);
    private static final JournalSummary INACTIVE_JOURNAL_SUMMARY = new StandardJournalSummary(-1L, -1L, 0);
    private static final int JOURNAL_ENCODING_VERSION = 1;
    private static final byte TRANSACTION_FOLLOWS = 64;
    private static final byte JOURNAL_COMPLETE = 127;

    private final File journalFile;
    private final long initialTransactionId;
    private final SerDeFactory<T> serdeFactory;
    private final ObjectPool<ByteArrayDataOutputStream> streamPool;

    private SerDe<T> serde;
    private FileOutputStream fileOut;
    private BufferedOutputStream bufferedOut;

    private long currentTransactionId;
    private int transactionCount;
    private boolean headerWritten = false;

    private volatile boolean poisoned = false;
    private volatile boolean closed = false;
    private final BlockingQueue<JournalUpdate> updateQueue = new LinkedBlockingQueue<>();
    private final ByteBuffer transactionPreamble = ByteBuffer.allocate(13); // guarded by synchronized block

    public LengthDelimitedJournal(final File journalFile, final SerDeFactory<T> serdeFactory, final ObjectPool<ByteArrayDataOutputStream> streamPool, final long initialTransactionId)
            throws FileNotFoundException {
        this.journalFile = journalFile;
        this.serdeFactory = serdeFactory;
        this.serde = serdeFactory.createSerDe(null);
        this.streamPool = streamPool;

        this.initialTransactionId = initialTransactionId;
        this.currentTransactionId = initialTransactionId;
    }

    private synchronized OutputStream getOutputStream() throws FileNotFoundException {
        if (fileOut == null) {
            fileOut = new FileOutputStream(journalFile);
            bufferedOut = new BufferedOutputStream(fileOut);
        }

        return bufferedOut;
    }


    @Override
    public synchronized void writeHeader() throws IOException {
        try {
            final DataOutputStream outStream = new DataOutputStream(getOutputStream());
            outStream.writeUTF(SequentialAccessWriteAheadLog.class.getName());
            outStream.writeInt(JOURNAL_ENCODING_VERSION);

            serde = serdeFactory.createSerDe(null);
            outStream.writeUTF(serde.getClass().getName());
            outStream.writeInt(serde.getVersion());

            try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final DataOutputStream dos = new DataOutputStream(baos)) {

                serde.writeHeader(dos);
                dos.flush();

                final int serdeHeaderLength = baos.size();
                outStream.writeInt(serdeHeaderLength);
                baos.writeTo(outStream);
            }

            outStream.flush();
        } catch (final Exception e) {
            final IOException ioe = (e instanceof IOException) ? (IOException) e : new IOException("Failed to create journal file " + journalFile, e);

            logger.error("Failed to create new journal file {} due to {}", journalFile, ioe.toString(), ioe);
            poison(ioe);
        }

        headerWritten = true;

        //        final Thread updateJournalThread = new Thread(new Runnable() {
        //            private boolean flushed = false;
        //
        //            @Override
        //            public void run() {
        //                final BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut) {
        //                    @Override
        //                    public synchronized void flush() throws IOException {
        //                        super.flush();
        //                        flushed = true;
        //                    }
        //                };
        //
        //                final DataOutputStream out = new DataOutputStream(bufferedOut);
        //                final List<FutureLikeObject> notifyOnFlush = new ArrayList<>();
        //
        //                while (true) {
        //                    JournalUpdate update = null;
        //
        //                    try {
        //                        update = updateQueue.poll(1, TimeUnit.SECONDS);
        //                        if (update == null) {
        //                            continue;
        //                        }
        //
        //                        final ByteArrayOutputStream baos = update.getByteArrayOutputStream();
        //                        final long transactionId = update.getTransactionId();
        //
        //                        // TODO: Error handling is not right. If some completed but then an Exception as thrown, we may have written
        //                        // 5 transactions successfully, for instance, then failed. We have to combine all of these updates into a single
        //                        // transaction.
        //                        out.write(TRANSACTION_FOLLOWS);
        //                        out.writeLong(transactionId);
        //                        out.writeInt(baos.size());
        //                        baos.writeTo(out);
        //
        //                        if (updateQueue.isEmpty()) {
        //                            out.flush();
        //                        }
        //
        //                        notifyOnFlush.add(update.getFuture());
        //
        //                        if (flushed) {
        //                            for (final FutureLikeObject future : notifyOnFlush) {
        //                                future.complete();
        //                            }
        //
        //                            flushed = false;
        //                            notifyOnFlush.clear();
        //                        }
        //                    } catch (final Exception e) {
        //                        update.getFuture().completeExceptionally(e);
        //                        rejectQueue(e);
        //                        return;
        //                    }
        //
        //                    if (LengthDelimitedJournal.this.closed && updateQueue.isEmpty()) {
        //                        return;
        //                    }
        //                }
        //            }
        //
        //            private void rejectQueue(final Throwable cause) {
        //                JournalUpdate update;
        //                while ((update = updateQueue.poll()) != null) {
        //                    update.getFuture().completeExceptionally(cause);
        //                }
        //            }
        //        });
        //
        //        updateJournalThread.setName("Update Journal " + journalFile);
        //        updateJournalThread.start();
    }

    private synchronized SerDeAndVersion validateHeader(final DataInputStream in) throws IOException {
        final String writeAheadLogClassName = in.readUTF();
        logger.debug("Write Ahead Log Class Name for {} is {}", journalFile, writeAheadLogClassName);
        if (!SequentialAccessWriteAheadLog.class.getName().equals(writeAheadLogClassName)) {
            throw new IOException("Invalid header information - " + journalFile + " does not appear to be a valid journal file.");
        }

        final int encodingVersion = in.readInt();
        logger.debug("Encoding version for {} is {}", journalFile, encodingVersion);
        if (encodingVersion > JOURNAL_ENCODING_VERSION) {
            throw new IOException("Cannot read journal file " + journalFile + " because it is encoded using veresion " + encodingVersion
                + " but this version of the code only understands version " + JOURNAL_ENCODING_VERSION + " and below");
        }

        final String serdeClassName = in.readUTF();
        logger.debug("Serde Class Name for {} is {}", journalFile, serdeClassName);
        final SerDe<T> serde;
        try {
            serde = serdeFactory.createSerDe(serdeClassName);
        } catch (final IllegalArgumentException iae) {
            throw new IOException("Cannot read journal file " + journalFile + " because the serializer/deserializer used was " + serdeClassName
                + " but this repository is configured to use a different type of serializer/deserializer");
        }

        final int serdeVersion = in.readInt();
        logger.debug("Serde version is {}", serdeVersion);
        if (serdeVersion > serde.getVersion()) {
            throw new IOException("Cannot read journal file " + journalFile + " because it is encoded using veresion " + encodingVersion
                + " of the serializer/deserializer but this version of the code only understands version " + serde.getVersion() + " and below");
        }

        final int serdeHeaderLength = in.readInt();
        final InputStream serdeHeaderIn = new LimitingInputStream(in, serdeHeaderLength);
        final DataInputStream dis = new DataInputStream(serdeHeaderIn);
        serde.readHeader(dis);

        return new SerDeAndVersion(serde, serdeVersion);
    }


    @Override
    public long update(final Collection<T> records, final RecordLookup<T> recordLookup) throws IOException {
        if (!headerWritten) {
            throw new IllegalStateException("Cannot update journal file " + journalFile + " because no header has been written yet.");
        }

        checkState();

        // TODO: Add debug logging
        // TODO: Need to pass 'forceSync' flag in and do that here instead of at the higher level because it needs to happen before the background thread notifies the future.
        try {
            final ByteArrayDataOutputStream bados = streamPool.borrowObject();
            try {
                for (final T record : records) {
                    final Object recordId = serde.getRecordIdentifier(record);
                    final T previousRecordState = recordLookup.lookup(recordId);
                    serde.serializeEdit(previousRecordState, record, bados.getDataOutputStream());
                }

                final ByteArrayOutputStream baos = bados.getByteArrayOutputStream();
                final OutputStream out = getOutputStream();

                final long transactionId;
                synchronized (this) {
                    transactionId = currentTransactionId++;
                    transactionCount++;

                    transactionPreamble.clear();
                    transactionPreamble.putLong(transactionId);
                    transactionPreamble.putInt(baos.size());
                    transactionPreamble.put(TRANSACTION_FOLLOWS);
                    out.write(transactionPreamble.array());

                    baos.writeTo(out);
                    out.flush();
                }

                baos.reset();
                return transactionId;
            } finally {
                streamPool.returnObject(bados);
            }
        } catch (final IOException ioe) {
            poison(ioe);
            throw ioe;
        }
    }

    private void checkState() throws IOException {
        if (poisoned) {
            throw new IOException("Cannot update journal file " + journalFile + " because this journal has already encountered a failure when attempting to write to the file. "
                + "If the repository is able to checkpoint, then this problem will resolve itself. However, if the repository is unable to be checkpointed "
                + "(for example, due to being out of storage space or having too many open files), then this issue may require manual intervention.");
        }

        if (closed) {
            throw new IOException("Cannot update journal file " + journalFile + " because this journal has already been closed");
        }
    }

    private void poison(final IOException ioe) throws IOException {
        this.poisoned = true;

        try {
            if (fileOut != null) {
                fileOut.close();
            }

            closed = true;
        } catch (final IOException innerIOE) {
            ioe.addSuppressed(innerIOE);
            throw ioe;
        }
    }

    @Override
    public synchronized void fsync() throws IOException {
        checkState();

        try {
            if (fileOut != null) {
                fileOut.getChannel().force(false);
            }
        } catch (final IOException ioe) {
            poison(ioe);
        }
    }

    @Override
    public synchronized void close() throws IOException {
        if (closed) {
            return;
        }

        closed = true;

        try {
            if (fileOut != null) {
                if (!poisoned) {
                    fileOut.write(JOURNAL_COMPLETE);
                }

                fileOut.close();
            }
        } catch (final IOException ioe) {
            poison(ioe);
        }
    }

    @Override
    public JournalRecovery recoverRecords(final Map<Object, T> recordMap) throws IOException {
        // TODO: Add logging to indicate how much is complete
        long maxTransactionId = -1L;
        int updateCount = 0;

        try (final InputStream fis = new FileInputStream(journalFile);
            final InputStream bufferedIn = new BufferedInputStream(fis);
            final ByteCountingInputStream byteCountingIn = new ByteCountingInputStream(bufferedIn);
            final DataInputStream in = new DataInputStream(bufferedIn)) {

            final SerDeAndVersion serdeAndVersion = validateHeader(in);
            final SerDe<T> serde = serdeAndVersion.getSerDe();

            try {
                int transactionIndicator = in.read();
                while (transactionIndicator == TRANSACTION_FOLLOWS) {

                    final long transactionId = in.readLong();
                    maxTransactionId = Math.max(maxTransactionId, transactionId);

                    final int transactionLength = in.readInt();
                    final InputStream transactionLimitingIn = new LimitingInputStream(in, transactionLength);
                    final DataInputStream transactionDis = new DataInputStream(transactionLimitingIn);
                    serde.deserializeEdit(transactionDis, recordMap, serdeAndVersion.getVersion());
                    updateCount++;

                    transactionIndicator = in.read();

                    if (transactionIndicator != TRANSACTION_FOLLOWS && transactionIndicator != JOURNAL_COMPLETE && transactionIndicator != -1) {
                        throw new IOException("After reading " + byteCountingIn.getBytesConsumed() + " bytes from " + journalFile + ", encountered unexpected value of "
                            + transactionIndicator + " for the Transaction Indicator. This journal appears to have been corrupted");
                    }
                }
            } catch (final EOFException eof) {
                logger.warn("Encountered unexpected End-of-File when reading journal file {}; assuming that NiFi was shutdown unexpectedly and continuing recovery", journalFile);
            }
        }

        return new StandardJournalRecovery(updateCount, maxTransactionId);
    }

    @Override
    public synchronized JournalSummary getSummary() {
        if (transactionCount < 1) {
            return INACTIVE_JOURNAL_SUMMARY;
        }

        return new StandardJournalSummary(initialTransactionId, currentTransactionId - 1, transactionCount);
    }

    private class SerDeAndVersion {
        private final SerDe<T> serde;
        private final int version;

        public SerDeAndVersion(final SerDe<T> serde, final int version) {
            this.serde = serde;
            this.version = version;
        }

        public SerDe<T> getSerDe() {
            return serde;
        }

        public int getVersion() {
            return version;
        }
    }

    private static class JournalUpdate {
        private final long transactionId;
        private final ByteArrayOutputStream baos;
        private final FutureLikeObject future = new FutureLikeObject();

        public JournalUpdate(final long transactionId, final ByteArrayOutputStream baos) {
            this.transactionId = transactionId;
            this.baos = baos;
        }

        public long getTransactionId() {
            return transactionId;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return baos;
        }

        public FutureLikeObject getFuture() {
            return future;
        }
    }

    private static class FutureLikeObject2 {
        private Optional<Throwable> failure;

        public Throwable getFailure() throws InterruptedException {
            synchronized (this) {
                while (failure == null) {
                    this.wait();
                }

                return failure.orElse(null);
            }
        }

        public void completeExceptionally(final Throwable failure) {
            synchronized (this) {
                this.failure = Optional.ofNullable(failure);
                notify();
            }
        }

        public void complete() {
            synchronized (this) {
                this.failure = Optional.empty();
                notify();
            }
        }
    }

    private static class FutureLikeObject {
        private static final Exception EXCEPTION_TO_IGNORE = new Exception();
        private final TransferQueue<Throwable> queue = new LinkedTransferQueue<>();

        public Throwable getFailure() throws InterruptedException {
            final Throwable failure = queue.take();
            if (failure == EXCEPTION_TO_IGNORE) {
                return null;
            }
            return failure;
        }

        public void completeExceptionally(final Throwable failure) {
            queue.offer(failure);
        }

        public void complete() {
            queue.offer(EXCEPTION_TO_IGNORE);
        }
    }

    private static class FutureLikeObject3 {
        private static final Object MONITOR = new Object();
        private Optional<Throwable> failure;

        public Throwable getFailure() throws InterruptedException {
            synchronized (MONITOR) {
                while (failure == null) {
                    MONITOR.wait();
                }

                return failure.orElse(null);
            }
        }

        public void completeExceptionally(final Throwable failure) {
            synchronized (MONITOR) {
                this.failure = Optional.ofNullable(failure);
                MONITOR.notifyAll();
            }
        }

        public void complete() {
            synchronized (MONITOR) {
                this.failure = Optional.empty();
                MONITOR.notifyAll();
            }
        }
    }

    private static class FutureLikeObject5 {
        private volatile Optional<Throwable> failure;
        private final Thread thread = Thread.currentThread();

        public Throwable getFailure() throws InterruptedException {
            while (failure == null) {
                LockSupport.park(this);
            }

            return failure.orElse(null);
        }

        public void completeExceptionally(final Throwable failure) {
            this.failure = Optional.ofNullable(failure);
            LockSupport.unpark(thread);
        }

        public void complete() {
            this.failure = Optional.empty();
            LockSupport.unpark(thread);
        }
    }
}
