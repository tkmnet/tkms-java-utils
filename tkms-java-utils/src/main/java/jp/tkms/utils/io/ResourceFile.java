package jp.tkms.utils.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ResourceFile {
    private static ResourceFile staticInstance = new ResourceFile();

    public static BufferedInputStream newInputStream(String fileName) {
        return new BufferedInputStream(staticInstance.getClass().getResourceAsStream(fileName));
    }

    public static BufferedReader newBufferedReader(String fileName) {
        return new BufferedReader(new InputStreamReader(newInputStream(fileName)));
    }

    public static String readString(String fileName) throws IOException {
        try (BufferedInputStream inputStream = newInputStream(fileName)) {
            return IOStreamUtil.readString(inputStream);
        }
    }

    public static void copyFile(String fileName, Path destination) throws IOException {
        try (BufferedInputStream inputStream = newInputStream(fileName)) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destination.toFile()))) {
                IOStreamUtil.pipe(inputStream, outputStream);
            }
        }
    }

    public static void unzip(String fileName, Path destination) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(newInputStream(fileName))) {
            ZipEntry entry = null;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path entryPath = destination.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    try (OutputStream outputStream = new FileOutputStream(entryPath.toFile())) {
                        IOStreamUtil.pipe(zipInputStream, outputStream);
                    }
                }
            }
        }
    }
}
