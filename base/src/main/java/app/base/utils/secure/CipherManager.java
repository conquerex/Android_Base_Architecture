package app.base.utils.secure;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public final class CipherManager {
    private static final String TAG = "CipherManager";

    private final static char[] INIT_VECTOR = "0123456789ABCDEF".toCharArray();

    public static String encrypt(String modulus, String exponent, String value) {
        String result = "";
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Cannot encrypt");
        }

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            BigInteger modulusInt = new BigInteger(modulus, 16);
            BigInteger exponentInt = new BigInteger(exponent, 16);
            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modulusInt, exponentInt);

            PublicKey publicKey = keyFactory.generatePublic(pubSpec);

            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encByte = cipher.doFinal(value.getBytes());
            result = bytesToHex(encByte);

        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeySpecException
                | IllegalBlockSizeException e) {
            throw new RuntimeException("Cannot encrypt");
        }

        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        char[] hexChars = new char[bytes.length * 2];

        for (int index = 0; index < bytes.length; index++) {
            int v = bytes[index] & 0xFF;
            hexChars[index * 2] = INIT_VECTOR[v >>> 4];
            hexChars[index * 2 + 1] = INIT_VECTOR[v & 0x0F];
        }

        return String.valueOf(hexChars);
    }
}
