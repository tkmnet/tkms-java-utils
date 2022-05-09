package jp.tkms.utils.io;

import java.io.*;

public class IOStreamUtil {
    public static long pipe(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[8192];
        long size = 0;
        int n;
        if (inputStream != null) {
            while(-1 != (n = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
                size += n;
            }
        }
        inputStream.close();
        return size;
    }

    public static String readString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOStreamUtil.pipe(inputStream, outputStream);
        inputStream.close();
        return outputStream.toString();
    }
}
