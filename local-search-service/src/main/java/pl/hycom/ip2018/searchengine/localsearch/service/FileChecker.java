package pl.hycom.ip2018.searchengine.localsearch.service;

import pl.hycom.ip2018.searchengine.localsearch.exception.LocalSearchIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Shared methods which can describe file
 */
public class FileChecker {

    private static final Integer THRESHOLD = 95;

    /**
     * @param file input File
     * @return answer file is plain text or binary
     */
    public boolean isBinaryFile(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            int size = stream.available();
            if (size > 1024) {
                size = 1024;
            }
            byte[] data = new byte[size];
            stream.read(data);
            stream.close();
            int ascii = 0;
            int other = 0;
            for (byte b : data) {
                int temp = b & 0xFF;
                if (temp < 0x09) {
                    return true;
                }
                if (temp == 0x09 || temp == 0x0A || temp == 0x0C || temp == 0x0D) {
                    ascii++;
                } else if (temp >= 0x20 && temp <= 0x7E) {
                    ascii++;
                } else {
                    other++;
                }
            }
            return other != 0 && 100 * other / (ascii + other) > THRESHOLD;
        } catch (IOException ignored) {
            throw new LocalSearchIOException();
        }
    }
}
