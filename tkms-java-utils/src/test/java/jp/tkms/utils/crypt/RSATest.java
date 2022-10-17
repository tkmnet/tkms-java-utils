package jp.tkms.utils.crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSATest {
    @Test
    void success() {
        String plain = "OK_123";
        RSA rsa = new RSA();
        try {
            byte[] encrypted = RSA.encrypt(rsa.getPublicKey(), plain);
            assertEquals(plain, rsa.decryptToString(encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
        try {
            String encrypted = RSA.encryptToString(rsa.getPublicKey(), plain);
            assertEquals(plain, rsa.decryptToString(encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
        try {
            String encrypted = RSA.encryptToString(rsa.getPublicKey(), plain.getBytes());
            assertArrayEquals(plain.getBytes(), rsa.decrypt(encrypted));
        } catch (EncryptingException e) {
            throw new RuntimeException(e);
        } catch (DecryptingException e) {
            throw new RuntimeException(e);
        }
    }
}
