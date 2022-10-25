package jp.tkms.utils.string;

import java.util.Base64;

public class SafeString {
    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
