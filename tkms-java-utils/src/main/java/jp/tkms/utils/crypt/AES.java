package jp.tkms.utils.crypt;

import jp.tkms.utils.string.HashString;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    private static final IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec("FF23456789ABCD00".getBytes());

    public static byte[] encrypt(String key, byte[] data) throws EncryptingException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(HashString.toSHA256(key), "AES"), IV_PARAMETER_SPEC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EncryptingException(e);
        }
    }

    public static byte[] encrypt(String key, String data) throws EncryptingException {
        return encrypt(key, data.getBytes());
    }

    public static String encryptToString(String key, byte[] data) throws EncryptingException {
        return Base64.getEncoder().encodeToString(encrypt(key, data));
    }

    public static String encryptToString(String key, String data) throws EncryptingException {
        return Base64.getEncoder().encodeToString(encrypt(key, data));
    }

    public static byte[] decrypt(String key, byte[] data) throws DecryptingException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(HashString.toSHA256(key), "AES"), IV_PARAMETER_SPEC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new DecryptingException(e);
        }
    }

    public static byte[] decrypt(String key, String data) throws DecryptingException {
        return decrypt(key, Base64.getDecoder().decode(data));
    }

    public static String decryptToString(String key, byte[] data) throws DecryptingException {
        return new String(decrypt(key, data));
    }

    public static String decryptToString(String key, String data) throws DecryptingException {
        return new String(decrypt(key, data));
    }
}
