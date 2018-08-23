/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @since Dec 10, 2015 3:24:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WavRecorder implements AudioRecorder, Runnable {

    enum RecorderState {
        idle,
        recording,
        paused,
        terminating
    }
    private final Object lockObject = new Object();
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private volatile long recordedLength = 0;
    private final Thread recordingThread;
    private volatile boolean isRecording = false;
//    private boolean isTerminating = false;
    private volatile RecorderState recorderState = RecorderState.idle;
    private static final int TIMER_INTERVAL = 120;
    private static final int FRAME_PERIOD = RECORDER_SAMPLERATE * TIMER_INTERVAL / 1000;
    private static final int BUFFER_SIZE = FRAME_PERIOD * 2 * 16 * 1 / 8;
    private volatile File outputFile = null;
//    private volatile CallbackContext recentCallbackContext = null;

    public WavRecorder() {
//        bufferSize = 100 * AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
        recordingThread = new Thread(this, "recordingThread");
        recordingThread.start();
    }

    @Override
    public void run() {
        final AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, BUFFER_SIZE);
        recordedLength = 0;
        while (recorderState != RecorderState.terminating) {
            System.out.println("recording loop");
            try {
                synchronized (lockObject) {
                    try {
                        if (recorder.getState() != AudioRecord.STATE_INITIALIZED || recorderState == RecorderState.idle) {
                            System.out.println("recording thread going to sleep");
                            lockObject.wait(10000);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("recording thread woken");
                    }
                }
                if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                    while (recorderState == RecorderState.recording || recorderState == RecorderState.paused) {
                        if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                            // start the audio recording
                            recorder.startRecording();
                        }
                        final RandomAccessFile randomAccessFile;
                        synchronized (lockObject) {
                            System.out.println("outputFile: " + outputFile.getAbsolutePath());
                            randomAccessFile = new RandomAccessFile(outputFile, "rw");
                            outputFile = null;
                        }
                        randomAccessFile.seek(randomAccessFile.length());
                        // write a temporary wav header
                        writeWaveFileHeader(randomAccessFile);
                        // callbackContext.success(); // we cant call this final callback more than once
                        byte buffer[] = new byte[BUFFER_SIZE];
                        // if a new file is specified in outputFile then close the current file and start a new file but with the recorder running the entire time
                        long recordedLengthInner = 0;
                        while ((recorderState == RecorderState.recording || recorderState == RecorderState.paused) && outputFile == null) {
                            final int bytesRead = recorder.read(buffer, 0, buffer.length);
                            if (bytesRead > 0) {
                                randomAccessFile.write(buffer, 0, bytesRead);
                                recordedLengthInner = randomAccessFile.length() - 36;
                            }
                            synchronized (lockObject) {
                                isRecording = recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING;
                                recordedLength = recordedLengthInner;
                            }
//                            System.out.println("recordedLength: " + recordedLength);
//                            System.out.println("bytesRead: " + bytesRead);
//                            System.out.println("bufferSize: " + BUFFER_SIZE);
                            if (recorderState == RecorderState.paused) {
                                // when the app looses focus we enter pause and make sure that the wave headers are valid in case the app is terminated
                                writeWaveFileHeader(randomAccessFile);
                                while (recorderState == RecorderState.paused) {
                                    synchronized (lockObject) {
                                        try {
                                            System.out.println("recording thread going to sleep");
                                            lockObject.wait(10000);
                                        } catch (InterruptedException e) {
                                            System.out.println("recording thread woken");
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("recording ended");

                        writeWaveFileHeader(randomAccessFile);
                        randomAccessFile.close();
                    }
                    if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                        recorder.stop();
                    }
                }
                synchronized (lockObject) {
                    isRecording = false;
                }
            } catch (final IOException e) {
                System.out.println("IOException: " + e.getMessage());
                synchronized (lockObject) {
                    isRecording = false;
                }
//                if (recentCallbackContext != null) {
//                    recentCallbackContext.error(e.getMessage());
//                    recentCallbackContext = null;
//                }
            }
        }
        if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
            recorder.stop();
        }
        recorder.release();
    }

    public long getTime() {
        synchronized (lockObject) {
            return recordedLength / ((RECORDER_BPP / 8 /* recordedLength is in bytes */) * RECORDER_SAMPLERATE / 1000);
        }
    }

    public boolean isRecording() {
        synchronized (lockObject) {
            return isRecording;
        }
    }

    public String startRecording(final File outputDirectory, Context context) throws IOException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String timeString = dateFormat.format(date);
        final String baseName = timeString; // + UUID.randomUUID().toString();
        final String absolutePath;
        synchronized (lockObject) {
            // setting outputFile allows for new file names to be passed and for the recording to contine but into the new file after cleanly closing the previous file
            outputFile = new File(outputDirectory, baseName + AUDIO_RECORDER_FILE_EXT_WAV);
            absolutePath = outputFile.getAbsolutePath();
            recordedLength = outputFile.length();
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }
            System.out.println("outputFile: " + outputFile.getPath());
            recorderState = RecorderState.recording;
            lockObject.notify();
        }
        MediaScannerConnection.scanFile(context, new String[]{absolutePath}, null, null);
        return baseName;
    }

    public void stopRecording() throws IOException {
        System.out.println("stopRecording");
        synchronized (lockObject) {
            recorderState = RecorderState.idle;
            lockObject.notify();
        }
    }

    public void pauseRecorder() {
        synchronized (lockObject) {
            if (recorderState == RecorderState.recording) {
                // the paused state can only be reached from the recording state
                recorderState = RecorderState.paused;
                lockObject.notify();
            }
        }
    }

    public void resumeRecorder() {
        synchronized (lockObject) {
            if (recorderState == RecorderState.paused) {
                // the paused state always returns to the recording state
                recorderState = RecorderState.recording;
                lockObject.notify();
            }
        }
    }

    public void terminateRecorder() {
        synchronized (lockObject) {
            recorderState = RecorderState.terminating;
            lockObject.notify();
        }
    }

    private void writeWaveFileHeader(RandomAccessFile randomAccessFile) throws IOException {
        // rewrite the wav header
        long totalAudioLen = randomAccessFile.length() - 36;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = RECORDER_SAMPLERATE;
        int channels = 2;
        if (RECORDER_CHANNELS == AudioFormat.CHANNEL_IN_MONO) {
            channels = 1;
        }
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;

        randomAccessFile.seek(0);

        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (channels * 16 / 8);  // block align
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        randomAccessFile.write(header, 0, 44);

        randomAccessFile.seek(randomAccessFile.length());
    }
}
