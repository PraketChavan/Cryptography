package uk.ac.nottingham.cryptography.asymmetric.padding;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

// PKCS v1.5 Padding RFC 3447
public class PKCSv15 {
    public static BigInteger pad(BigInteger message, int bitLength) {
        // DER Sha256 encoding - header
        byte[] T = Arrays.copyOf(
                new byte[] { 0x30, 0x31, 0x30, 0x0D, 0x06, 0x09, 0x60,
                        (byte)0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02,
                        0x01, 0x05, 0x00, 0x04, 0x20 }, 51);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.toByteArray());
            // Copy hash into DER encoding
            System.arraycopy(md.digest(), 0, T, 19, 32);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }

        // Calculate maximum byte length < modulus bit length
        int paddingByteLength = ((bitLength - 1) / 8);

        byte[] padding = new byte[paddingByteLength];

        // PKCS1
        padding[0] = 0x00;
        padding[1] = 0x01;
        Arrays.fill(padding, 2, padding.length - T.length - 1, (byte)0xFF);
        System.arraycopy(T, 0, padding, padding.length - T.length, T.length);

        // Return as numerical representation for signing
        return new BigInteger(padding);
    }

    private PKCSv15() {}
}
