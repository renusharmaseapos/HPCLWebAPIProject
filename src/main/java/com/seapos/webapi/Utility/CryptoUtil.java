package com.seapos.webapi.Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class CryptoUtil {

    private static final String ALGORITHM = "AES";
    private static final byte[] KEY =
            "MySecretKey12345".getBytes(StandardCharsets.UTF_8);

    private CryptoUtil() {}

    public static String encryptBase64(String input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(KEY, ALGORITHM));
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Encryption failed", ex);
        }
    }
}
