package io.descoped.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class ConsoleProgress implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ConsoleProgress.class);
    private final File file;
    private final long contentLength;

    public ConsoleProgress(File file, long contentLength) {
        this.file = file;
        this.contentLength = contentLength;
    }

    public static Thread consoleProgressThread(File file, long contentLength) {
        Thread progressThread = new Thread(new ConsoleProgress(file, contentLength));
        progressThread.start();
        return progressThread;
    }

    // http://stackoverflow.com/questions/1001290/console-based-progress-in-java
    public static void printProgress(long startTime, long total, long current) {
        long eta = current == 0 ? 0 :
                (total - current) * (System.currentTimeMillis() - startTime) / current;

        String etaHms = current == 0 ? "N/A" :
                String.format("%02d:%02d:%01d", TimeUnit.MILLISECONDS.toHours(eta),
                        TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

        System.out.print(string);
        if (percent % 25 == 0) System.out.println();
//        log.info("{}", string);
    }

    public static void interruptProgress(Thread progressThread) {
        progressThread.interrupt();
        try {
            Thread.currentThread().sleep(50);
        } catch (InterruptedException e) {
            log.warn("Progress interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                printProgress(startTime, contentLength, (file.length() == 0 ? 1 : file.length()));
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                printProgress(startTime, contentLength, (file.length() == 0 ? 1 : file.length()));
                System.out.println();
                break;
            }
        }
    }
}
