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

    public static void createIfNotExists (Path path, String string) throws IOException {
        createWithStringIfNotExists(path, null);
    }
}
