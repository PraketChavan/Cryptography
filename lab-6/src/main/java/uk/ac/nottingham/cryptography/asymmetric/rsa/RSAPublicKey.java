package uk.ac.nottingham.cryptography.asymmetric.rsa;

import uk.ac.nottingham.cryptography.asymmetric.padding.PKCSv15;

import java.math.BigInteger;

public class RSAPublicKey {
    private BigInteger n;
    private BigInteger e;

    public RSAPublicKey(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public boolean verify(BigInteger message, BigInteger signature) {
        BigInteger undo = signature.modPow(e, n);

        return message.compareTo(undo) == 0;
    }
}
