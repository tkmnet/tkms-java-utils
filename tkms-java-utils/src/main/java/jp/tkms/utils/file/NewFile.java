package jp.tkms.utils.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewFile {
  public static void createWithStringIfNotExists (Path path, String string) throws IOException {
    if (Files.notExists(path)) {
      Files.writeString(path, string == null ? "" : string);
    }
  }

  public static void createIfNotExists (Path path) throws IOException {
    createWithStringIfNotExists(path, null);
  }

  public static String createWithStringOrRead (Path path, String string) throws IOException {
    String result = (string == null ? "" : string);
    if (Files.notExists(path)) {
      Files.writeString(path, result);
    } else {
      result = Files.readString(path);
    }
    return result;
  }

  public static String createOrRead (Path path) throws IOException {
    return createWithStringOrRead(path, null);
  }
}
