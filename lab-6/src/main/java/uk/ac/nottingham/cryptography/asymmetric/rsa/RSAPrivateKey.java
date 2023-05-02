package uk.ac.nottingham.cryptography.asymmetric.rsa;

import uk.ac.nottingham.cryptography.asymmetric.padding.PKCSv15;

import java.math.BigInteger;

public class RSAPrivateKey {
    private BigInteger n;
    private BigInteger d;

    public RSAPrivateKey(BigInteger n, BigInteger d) {
        this.n = n;
        this.d = d;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger sign(BigInteger message) {
//        BigInteger messagePad = PKCSv15.pad(message, message.bitLength() + 1);
        return message.modPow(d, n);
    }
}
