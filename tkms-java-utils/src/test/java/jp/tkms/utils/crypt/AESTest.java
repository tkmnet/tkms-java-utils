package jp.tkms.utils.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AESTest {
    @Test
    void success() {
        String key = "123";
        String plain = "OK_123";
        try {
            byte[] encrypted = AES.encrypt(key, plain);
            assertEquals(plain, AES.decryptToString(key, encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
        try {
            String encrypted = AES.encryptToString(key, plain);
            assertEquals(plain, AES.decryptToString(key, encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
        try {
            String encrypted = AES.encryptToString(key, plain.getBytes());
            assertArrayEquals(plain.getBytes(), AES.decrypt(key, encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
    }
}
