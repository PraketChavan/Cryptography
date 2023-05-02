package uk.ac.nottingham.cryptography.asymmetric.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyPair {
    private final SecureRandom rand;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeyPair(int bitLength) {
        this.rand = new SecureRandom();
        generateKeyPair(bitLength);
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    private void generateKeyPair(int bitLength) {
        BigInteger p = BigInteger.probablePrime(bitLength / 2, rand);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, rand);
        BigInteger n = q.multiply(p);
        BigInteger t = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        BigInteger d = e.modInverse(t);
        publicKey = new RSAPublicKey(n, e);
        privateKey = new RSAPrivateKey(n, d);

    }
}
