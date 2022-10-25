package jp.tkms.utils.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashString {
  public static byte[] toSHA256(String text) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    messageDigest.update(text.getBytes());
    return messageDigest.digest();
  }

  public static String toSHA256Base64(String text) {
    return SafeString.toBase64(toSHA256(text));
  }
}
