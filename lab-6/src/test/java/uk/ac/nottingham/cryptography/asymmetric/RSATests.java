package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;
import uk.ac.nottingham.cryptography.asymmetric.rsa.RSAKeyPair;
import uk.ac.nottingham.cryptography.asymmetric.rsa.RSAPrivateKey;
import uk.ac.nottingham.cryptography.asymmetric.rsa.RSAPublicKey;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class RSATests {

    @Test
    void keyGenTest() {
        for (int bitLength : new int[] { 1024, 2048, 4096 }) {
            RSAKeyPair keyPair = new RSAKeyPair(bitLength);

            RSAPublicKey pub = keyPair.getPublicKey();
            assertNotNull(pub);

            RSAPrivateKey prv = keyPair.getPrivateKey();
            assertNotNull(prv);

            // Check inverse
            BigInteger e = pub.getE();
            BigInteger d = prv.getD();
            BigInteger n = pub.getN();

            assertTrue(() ->
            {
                BigInteger g = BigInteger.valueOf(314159265358979L);
                BigInteger gPrime = g.modPow(e,n).modPow(d,n);
                return g.compareTo(gPrime) == 0;
            });
        }
    }

    @Test
    void signatureTest() {

        for (int bitLength : new int[] { 1024, 2048, 4096 })
        {
            RSAKeyPair keyPair = new RSAKeyPair(bitLength);

            RSAPublicKey pub = keyPair.getPublicKey();
            assertNotNull(pub);

            RSAPrivateKey prv = keyPair.getPrivateKey();
            assertNotNull(prv);

            // Check signature
            BigInteger message = BigInteger.valueOf(314159265358979L);
            BigInteger signature = prv.sign(message);
            BigInteger invalidSignature1 = signature.subtract(BigInteger.ONE);
            BigInteger invalidSignature2 = signature.subtract(BigInteger.TEN);

            assertTrue(pub.verify(message, signature));
            assertFalse(pub.verify(message, invalidSignature1));
            assertFalse(pub.verify(message, invalidSignature2));
        }
    }
}
