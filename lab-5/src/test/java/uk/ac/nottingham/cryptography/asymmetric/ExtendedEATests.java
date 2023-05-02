package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedEATests {
    @Test
    void EEATests() {
        int[] r0 = new int[] { 121, 973, 2745, 47927, 798223 };
        int[] r1 = new int[] { 74, 405, 1134, 23364, 124432 };
        int[] s = new int[] {-11, 82, -19, 721, 47263};
        int[] t = new int[] { 18, -197, 46, -1479, -303189 };
        int[] gcd = new int[] { 1, 1, 9, 11, 1 };

        for (int i = 0; i < r0.length; i++) {
            EEAResult result = Utils.extendedEuclideanAlgorithm(r0[i],r1[i]);
            assertEquals(s[i], result.getS());
            assertEquals(t[i], result.getT());
            assertEquals(gcd[i], result.getR());
        }
    }

    @Test
    void randomTests() {
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            int r0 = Math.floorMod(rand.nextInt(), Integer.MAX_VALUE);
            int r1 = Math.floorMod(rand.nextInt(), Integer.MAX_VALUE);

            EEAResult result = Utils.extendedEuclideanAlgorithm(r0, r1);
            System.out.println(r0 + " " + r1);
            System.out.println(result.getR() + " " + result.getS() + " " + result.getT());
            long val = (long)r0 * result.getS() + (long)r1 * result.getT();
            assertEquals(result.getR(), val);
        }
    }

}
