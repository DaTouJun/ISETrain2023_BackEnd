package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA1Generator {
    public static String getSHA1(String code){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte [] result = messageDigest.digest(code.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : result) {
            stringBuilder.append(
                    Integer.toString((b & 0xff) + 0x100, 16)
                            .substring(1)
            );
        }
        return stringBuilder.toString();
    }
}
