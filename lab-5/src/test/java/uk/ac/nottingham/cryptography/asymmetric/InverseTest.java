package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InverseTest {
    @Test
    void inverseTest() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int r0 = Math.floorMod(rand.nextInt(), Integer.MAX_VALUE);
            int r1 = Math.floorMod(rand.nextInt(), Integer.MAX_VALUE);

            if (r1 > r0) {
                int temp = r0;
                r0 = r1;
                r1 = temp;
            }

            r0 = 3648241;
            r1 = 3;
            long val = ((long)r0 * Utils.multiplicativeInverse(r1, r0)) % r1;
            assertEquals(1, val);
        }
    }
}
