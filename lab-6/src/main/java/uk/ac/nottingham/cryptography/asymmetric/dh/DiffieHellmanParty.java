package uk.ac.nottingham.cryptography.asymmetric.dh;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class DiffieHellmanParty {
    private final SecureRandom rand;
    private final SafeDHParameters parameters;

    private BigInteger privateKey;
    private BigInteger publicKey;

    private BigInteger sharedSecret;

    public DiffieHellmanParty(SafeDHParameters params) {
        this.parameters = params;
        this.rand = new SecureRandom();
    }

    public BigInteger getPublicKey() {
        return this.publicKey;
    }

    public void generateKeyPair() {
        privateKey = randomBigInteger(BigInteger.TWO, parameters.getG().subtract(BigInteger.ONE), new SecureRandom());
        publicKey = parameters.getG().modPow(privateKey, parameters.getP());
    }

    public void exchange(BigInteger otherPublicKey) {
        this.sharedSecret = otherPublicKey.modPow(privateKey, parameters.getP());
    }

    public static BigInteger randomBigInteger(BigInteger min, BigInteger max, Random rand) {
        int bitlength = max.toByteArray().length;
        byte[] bytes = new byte[bitlength];
        rand.nextBytes(bytes);
        BigInteger random = new BigInteger(bytes);
        while (random.compareTo(max) > 0) {
            rand.nextBytes(bytes);
            random = new BigInteger(bytes);
        }
        return random;
    }

    public byte[] sharedSecretAsHash() {
        // This helper function lets us compare two shared secrets to see if the handshake worked
        if (this.sharedSecret == null) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(this.sharedSecret.toByteArray());
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

}
