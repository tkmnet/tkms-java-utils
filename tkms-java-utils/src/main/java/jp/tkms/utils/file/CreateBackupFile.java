package jp.tkms.utils.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CreateBackupFile {
    private static boolean removerScheduled = false;
    private static LinkedHashMap<Path, Path> backupFileMap = new LinkedHashMap<>();

    public static void removeIfNotChanged(Path source, Path destination) throws IOException {
        source = source.toAbsolutePath().normalize();
        destination = destination.toAbsolutePath().normalize();
        if (source.equals(destination)) {
            throw new RuntimeException("The source and Destination must be different : " + source);
        }

        if (backupFileMap.containsKey(source)) {
            if (FilesCompare.compare(source, destination)) {
                Files.deleteIfExists(destination);
            }
        }

        Files.createDirectories(destination.getParent());
        Files.copy(source, destination);
        backupFileMap.put(source, destination);

        if (!removerScheduled) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                for (Map.Entry<Path, Path> entry : backupFileMap.entrySet()) {
                    try {
                        if (FilesCompare.compare(entry.getKey(), entry.getValue())) {
                            Files.deleteIfExists(entry.getValue());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
            removerScheduled = true;
        }
    }
}
