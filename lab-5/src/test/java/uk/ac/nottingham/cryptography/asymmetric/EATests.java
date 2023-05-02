package uk.ac.nottingham.cryptography.asymmetric;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EATests {
    @Test
    void standardEATests () {
        int[][] inputs = new int[][]{{47, 12}, {273, 45}, {2483, 91},
                {10070, 2700}, {13468, 24}, {200114, 173}, {5905320, 1462062}};
        int[] outputs = new int[] {1, 3, 13, 10, 4, 1, 6};

        for (int i = 0; i < inputs.length; i++) {
            assertEquals(outputs[i], Utils.euclideanAlgorithm(inputs[i][0], inputs[i][1]));
        }
    }

    @Test
    void reverseTests() {
        assertEquals(2, Utils.euclideanAlgorithm(2,10));
        assertEquals(10, Utils.euclideanAlgorithm(10,100));
        assertEquals(1, Utils.euclideanAlgorithm(401,2519));
    }
}
