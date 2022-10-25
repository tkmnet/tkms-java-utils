package jp.tkms.utils.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class FilesCompare implements Callable<Boolean> {
  int EOF = -1;
  File file1;
  File file2;

  public FilesCompare(File file1, File file2) {
    this.file1 = file1;
    this.file2 = file2;
  }

  @Override
  public Boolean call() throws IOException {
    if (file2 == null || !file1.exists() || !file2.exists()) {
      return false;
    }
    if (file1.isDirectory()) {
      if (!file2.isDirectory()) {
        return false;
      }
      return true;
    }
    if (!Files.isReadable(file1.toPath()) || !Files.isReadable(file2.toPath())) {
      return false;
    }
    if (file1.length() != file2.length()) {
      return false;
    }
    try (
      InputStream ownFileStream = new FileInputStream(file1);
      InputStream targetFileStream = new FileInputStream(file2);
    ) {
      BufferedInputStream ownFileBufferedStream = new BufferedInputStream(ownFileStream);
      BufferedInputStream targetFileBufferedStream = new BufferedInputStream(targetFileStream);
      for (int ch = ownFileBufferedStream.read(); EOF != ch; ch = ownFileBufferedStream.read()) {
        if (ch != targetFileBufferedStream.read()) {
          return false;
        }
      }
      if (targetFileBufferedStream.read() != EOF) {
        return false;
      }
      ownFileBufferedStream.close();
      targetFileBufferedStream.close();
    }
    return true;
  }

  public static Boolean compare(Path path1, Path path2) throws IOException {
    return new FilesCompare(path1.toFile(), path2.toFile()).call();
  }
}
