package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;
import uk.ac.nottingham.cryptography.asymmetric.dh.DiffieHellmanParty;
import uk.ac.nottingham.cryptography.asymmetric.dh.SafeDHParameters;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class DHTests {

    @Test
    void exchangeTest() {
        // Does an exchange with each of the group parameters
        for (SafeDHParameters params : new SafeDHParameters[]{SafeDHParameters.OAKLEY_GROUP_14, SafeDHParameters.FFDHE_2048, SafeDHParameters.FFDHE_4096}) {

            DiffieHellmanParty alice = new DiffieHellmanParty(params);
            DiffieHellmanParty bob = new DiffieHellmanParty(params);

            alice.generateKeyPair();
            bob.generateKeyPair();

            BigInteger alicePublic = alice.getPublicKey();
            assertNotNull(alicePublic);

            BigInteger bobPublic = bob.getPublicKey();
            assertNotNull(bobPublic);

            alice.exchange(bob.getPublicKey());
            bob.exchange(alice.getPublicKey());

            byte[] aliceDigest = alice.sharedSecretAsHash();
            byte[] bobDigest = bob.sharedSecretAsHash();

            assertNotNull(aliceDigest);
            assertArrayEquals(aliceDigest, bobDigest);
        }
    }
}
