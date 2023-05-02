package uk.ac.nottingham.cryptography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTests {
    @Test
    void testMain() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        });
    }
}
