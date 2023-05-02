package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryExponentiationTests {
    @Test
    void modPowTests() {
        int[][] aen = new int[][] {
                {17, 14, 304},
                {56, 901, 2480},
                {422, 12720, 14509},
                {9845, 210450, 1280665},
                {4589301, 1265790, 1025735874},
                {275514031, 265312493, 655329049}
        };

        int[] results = new int[] {
                177,
                1296,
                12475,
                767265,
                386998917,
                2
        };

        for (int i = 0; i < aen.length; i++) {
            assertEquals(results[i], Utils.modPow(aen[i][0], aen[i][1], aen[i][2]));
        }
    }
}
