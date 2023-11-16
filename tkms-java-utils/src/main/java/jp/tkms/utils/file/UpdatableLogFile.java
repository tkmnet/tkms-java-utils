package jp.tkms.utils.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class UpdatableLogFile implements  AutoCloseable {
  RandomAccessFile file;
  int lastLineLength = 0;

  public UpdatableLogFile(Path path) throws IOException {
    this.file = new RandomAccessFile(path.toFile(), "rw");
  }

  private long seekToLastLineTop() throws IOException {
    lastLineLength = 0;
    long pos = file.length();
    file.seek(pos);
    while (pos > 0 && file.read() != '\n') {
      lastLineLength += 1;
      pos -= 1;
      file.seek(pos);
    }
    if (lastLineLength > 0) {
      if (pos > 0) pos += 1;
      file.seek(pos);
    }
    return pos;
  }

  public String getLastLine() throws IOException {
    seekToLastLineTop();
    byte[] line = new byte[lastLineLength];
    file.read(line);
    return new String(line, Charset.defaultCharset());
  }

  public void addLine(String text) throws IOException {
    file.seek(file.length());
    if (file.length() > 0) file.write('\n');
    file.write(text.getBytes(Charset.defaultCharset()));
  }

  public void updateLastLine(String text) throws IOException {
    file.setLength(seekToLastLineTop());
    file.write(text.getBytes(Charset.defaultCharset()));
  }

  @Override
  public void close() throws Exception {
    file.close();
  }
}
