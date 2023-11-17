package jp.tkms.utils.file;

import jp.tkms.utils.debug.DebugString;
import jp.tkms.utils.value.IntRange;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatableLogFileTest {
    @Test
    void success() {
      try {
        Path path = Files.createTempFile("test", "tkms");
        path.toFile().deleteOnExit();
        try (UpdatableLogFile file = new UpdatableLogFile(path)) {
          assertEquals("", file.getLastLine());
          file.updateLastLine("zxc");
          assertEquals("zxc", file.getLastLine());
          file.updateLastLine("abc");
          file.updateLastLine("abc");
          file.updateLastLine("abc");
          file.updateLastLine("abc");
          file.updateLastLine("abc");
          assertEquals("abc", file.getLastLine());
          file.addLine("123!!!");
          file.updateLastLine("abc!!!");
          file.updateLastLine("123");
          file.addLine("qwe");
        }
        assertArrayEquals("abc\n123\nqwe".getBytes(Charset.defaultCharset()), Files.readAllBytes(path));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      try {
        Path path = Files.createTempFile("test", "tkms");
        path.toFile().deleteOnExit();
        try (UpdatableLogFile file = new UpdatableLogFile(path)) {
          file.addLine("abc");
          file.addLine("1234");
          file.addLine("qwe");
        }
        assertArrayEquals("abc\n1234\nqwe".getBytes(Charset.defaultCharset()), Files.readAllBytes(path));

        try (UpdatableLogFile file = new UpdatableLogFile(path)) {
          file.updateLastLine("ab");
          file.addLine("234");
          file.addLine("we");
        }
        assertArrayEquals("abc\n1234\nab\n234\nwe".getBytes(Charset.defaultCharset()), Files.readAllBytes(path));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
}
