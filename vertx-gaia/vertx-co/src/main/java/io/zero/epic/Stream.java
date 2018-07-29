package io.zero.epic;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.zero.exception.heart.EmptyStreamException;
import io.vertx.zero.log.Log;
import io.zero.epic.fn.Fn;

import java.io.*;

/**
 * Stream read class.
 */
final class Stream {
    /**
     * Direct read by vert.x logger to avoid dead lock.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Stream.class);

    private Stream() {
    }

    /**
     * Codec usage
     *
     * @param message The java object that will be converted from.
     * @param <T>     Target java object that will be converted to.
     * @return Target java object ( Generic Type )
     */
    static <T> byte[] to(final T message) {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        return Fn.getJvm(new byte[0], () -> {
            final ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(message);
            out.close();
            return bytes.toByteArray();
        }, bytes);
    }

    /**
     * Codec usage
     *
     * @param pos    The position of reading
     * @param buffer The buffer to hold the data from reading.
     * @param <T>    The converted java object type, Generic Type
     * @return Return to converted java object.
     */
    @SuppressWarnings("unchecked")
    static <T> T from(final int pos, final Buffer buffer) {
        final ByteArrayInputStream stream = new ByteArrayInputStream(buffer.getBytes());
        return Fn.getJvm(null, () -> {
            final ObjectInputStream in = new ObjectInputStream(stream);
            return (T) in.readObject();
        }, stream);
    }

    /**
     * @param filename The filename to describe source path
     * @return Return the InputStream object mount to source path.
     */
    static InputStream read(final String filename) {
        return read(filename, null);
    }

    /**
     * Ensure read from path
     * 1. Read from current folder
     * 2. clazz == null: Read from class loader
     * 3. clazz != null: Read from clazz's class loader
     *
     * @param filename The filename to describe source path
     * @param clazz    The class loader related class
     * @return Return the InputStream object mount to source path.
     */
    static InputStream read(final String filename,
                            final Class<?> clazz) {
        final File file = new File(filename);
        Log.debug(LOGGER, Info.INF_CUR, file.exists());
        final InputStream in = Fn.getSemi(file.exists(), null,
                () -> in(file),
                () -> (null == clazz) ? in(filename) : in(filename, clazz));
        Log.debug(LOGGER, Info.INF_PATH, filename, in);
        if (null == in) {
            throw new EmptyStreamException(filename);
        }
        return in;
    }

    /**
     * Stream read from file object
     * new FileInputStream(file)
     *
     * @param file The file object to describe source path
     * @return Return the InputStream object mount to source path.
     */
    static InputStream in(final File file) {
        return Fn.getJvm(() -> (file.exists() && file.isFile())
                ? new FileInputStream(file) : null, file);
    }

    /**
     * Stream read from clazz
     * clazz.getResourceAsStream(filename)
     *
     * @param filename The filename to describe source path
     * @param clazz    The class loader related class
     * @return Return the InputStream object mount to source path.
     */
    static InputStream in(final String filename,
                          final Class<?> clazz) {
        return Fn.getJvm(
                () -> clazz.getResourceAsStream(filename), clazz, filename);
    }

    /**
     * Stream read from Thread Class Loader
     * Thread.currentThread().getContextClassLoader()
     *
     * @param filename The filename to describe source path
     * @return Return the InputStream object mount to source path.
     */
    static InputStream in(final String filename) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return Fn.getJvm(
                () -> loader.getResourceAsStream(filename), filename);
    }
}
