package jp.tkms.utils.crypt;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSA() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            KeyFactory factory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicKeySpec = factory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
            RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
            publicKey = factory.generatePublic(publicKeySpec);
            privateKey = factory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public byte[] decrypt(byte[] encrypted) throws DecryptingException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new DecryptingException(e);
        }
    }

    public byte[] decrypt(String encrypted) throws DecryptingException {
        return decrypt(Base64.getDecoder().decode(encrypted));
    }

    public String decryptToString(byte[] encrypted) throws DecryptingException {
        return new String(decrypt(encrypted));
    }

    public String decryptToString(String encrypted) throws DecryptingException {
        return new String(decrypt(encrypted));
    }

    public static byte[] encrypt(String key, byte[] data) throws EncryptingException {
        Cipher cipher = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
            PublicKey publicKey = factory.generatePublic(keySpec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] encrypted = new byte[0];
        try {
            encrypted = cipher.doFinal(data);
        } catch (Exception e) {
            throw new EncryptingException(e);
        }
        return encrypted;
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
}
