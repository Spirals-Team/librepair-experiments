package cn.productai.api.core.helper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zhong Wang on 2017/7/3.
 *
 */
public class FileHelper {
    private final static Charset _charset = Charset.forName("UTF-8");

    public static String getBoundary() {
        return "---------------------------boundary" + String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String getContentType(String boundary) {
        return "multipart/form-data; boundary=" + boundary;
    }

    public static byte[] getMultipartBytes(File file, String boundary, HashMap<String, String> options, String paraName) throws IOException {
        ArrayList<Byte> bytes = new ArrayList<>();
        addRange(bytes, boundaryBytes(boundary));
        if (options != null && options.size() > 0) {
            for (String key : options.keySet()) {
                addRange(bytes, fieldBytes(key, options.get(key), boundary));
            }
        }
        addRange(bytes, fileHeaders(file, paraName));
        addRange(bytes, getFileAllBytes(file));
        addRange(bytes, tailBytes(boundary));
        byte[] buffer = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            buffer[i] = bytes.get(i);
        }
        return buffer;
    }

    private static void addRange(ArrayList<Byte> list, byte[] arrayToAdd) {
        for (byte b : arrayToAdd) {
            list.add(b);
        }
    }

    private static byte[] boundaryBytes(String boundary) {
        return ("\r\n--" + boundary + "\r\n").getBytes(_charset);
    }

    private static byte[] fileHeaders(File file, String paraName) {
        String header = "Content-Disposition: form-data;";
        header += String.format(" name=\"%s\";", paraName);
        header += String.format(" filename=\"%s\"\r\n", file.getName());
        header += String.format("Content-Type: %s\r\n\r\n", getFileType(file));
        return header.getBytes(_charset);
    }

    private static byte[] fieldBytes(String key, String value, String boundary) {
        String field = "Content-Disposition: form-data;";
        field += String.format(" name=\"%s\"\r\n\r\n%s", key, value);
        field += String.format("\r\n--%s\r\n",boundary);
        return field.getBytes(_charset);
    }

    private static byte[] tailBytes(String boundary) {
        String tail = String.format("\r\n--%s--\r\n", boundary);
        return tail.getBytes(_charset);
    }

    private static String getFileType(File _file) {
        String filename = _file.getName();
        String extension = filename.substring(filename.lastIndexOf("."));
        switch (extension.toLowerCase()) {
            case ".png":
                return "image/png";
            case ".jpg":
                return "image/jpeg";
            case ".gif":
                return "image/gif";
            case ".bmp":
                return "image/bmp";
            case ".csv":
                return "application/vnd.ms-excel";
            case ".txt":
                return "text/plain";
            case ".xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }

        return "text/plain";
    }

    private static byte[] getFileAllBytes(File file) throws IOException {
        byte[] buffer;

        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((1024));
        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        inputStream.close();
        outputStream.close();
        buffer = outputStream.toByteArray();

        return buffer;
    }
}
