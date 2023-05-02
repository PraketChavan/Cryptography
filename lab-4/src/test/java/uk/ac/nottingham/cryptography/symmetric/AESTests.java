package uk.ac.nottingham.cryptography.symmetric;

import org.junit.jupiter.api.Test;
import uk.ac.nottingham.cryptography.symmetric.AES;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AESTests {
    static final Random rand;

    static {
        rand = new Random(3077L);
    }

    @Test
    void addKeyTest() {
        byte[][] originalBlocks = new byte[][] {{ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F},
                { (byte)0xF0, (byte)0xE1, (byte)0xD2, (byte)0xC3, (byte)0xB4, (byte)0xA5, (byte)0x96, (byte)0x87, 0x78, 0x69, 0x5A, 0x4B, 0x3C, 0x2D, 0x1E, 0x0F},
                { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F}};

        byte[][] keyBlocks = new byte[][] { { (byte)0xF0, (byte)0xE1, (byte)0xD2, (byte)0xC3, (byte)0xB4, (byte)0xA5, (byte)0x96, (byte)0x87, 0x78, 0x69, 0x5A, 0x4B, 0x3C, 0x2D, 0x1E, 0x0F},
                { (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC, (byte)0xAC},
                { (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88, 0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00}};

        byte[][] addedBlocks = new byte[][] { {(byte)0xF0,(byte)0xE0,(byte)0xD0,(byte)0xC0,(byte)0xB0,(byte)0xA0,(byte)0x90,(byte)0x80,0x70,0x60,0x50,0x40,0x30,0x20,0x10,0x00},
                {0x5C,0x4D,0x7E,0x6F,0x18,0x09,0x3A,0x2B,(byte)0xD4,(byte)0xC5,(byte)0xF6,(byte)0xE7,(byte)0x90,(byte)0x81,(byte)0xB2,(byte)0xA3},
                {(byte)0xFF,(byte)0xEF,(byte)0xDF,(byte)0xCF,(byte)0xBF,(byte)0xAF,(byte)0x9F,(byte)0x8F,0x7F,0x6F,0x5F,0x4F,0x3F,0x2F,0x1F,0x0F} };

        AES aes = new AES();

        for (int i = 0; i < originalBlocks.length; i++) {
            byte[] block = Arrays.copyOf(originalBlocks[i], originalBlocks[i].length);

            // Add key
            aes.AddKey(block, keyBlocks[i]);
            assertArrayEquals(addedBlocks[i], block);

            // Add key again, should invert
            aes.AddKey(block, keyBlocks[i]);
            assertArrayEquals(originalBlocks[i], block);
        }
    }

    @Test
    void gmul2Test() {
        AES aes = new AES();

        byte[] inputs = new byte[] { -125, -80, -45, -9, 0, 1, 17, 45, 67, 127};
        byte[] outputs = new byte[] {29, 123, -67, -11, 0, 2, 34, 90, -122, -2};

        for (int i = 0; i < inputs.length; i++) {
            assertEquals(outputs[i], aes.gmul2(inputs[i]));
        }
    }

    @Test
    void gmul3Test() {
        AES aes = new AES();

        byte[] inputs = new byte[] { -125, -80, -45, -9, 0, 1, 17, 45, 67, 127};
        byte[] outputs = new byte[] {-98, -53, 110, 2, 0, 3, 51, 119, -59, -127};

        for (int i = 0; i < inputs.length; i++) {
            assertEquals(outputs[i], aes.gmul3(inputs[i]));
        }
    }

    @Test
    void gmulTest() {
        AES aes = new AES();

        byte[][] inputs = new byte[][] { {0, 1}, {0, 100}, {1, 2}, {2, 2}, {6, 19}, {100, 50},
                {26, (byte)250}, {(byte)255, (byte)255}, {12, 64}, {(byte)128, 7},{78,16},
                {106,(byte)146}, {(byte)168,(byte)170}, {122,59}, {99,51}, {118,52}, {(byte)175,42},
                {(byte)235,(byte)133}, {25,49}, {(byte)254,114}, {(byte)207,53}, {(byte)234,22}};

        byte[] outputs =  new byte[] {0, 0, 2, 4, 106, (byte)230, 71, 19, 45, (byte)173, (byte)140,(byte)191,
                (byte)253, 75, 27, (byte)160, 118, (byte)225, (byte)159, (byte)147,
                (byte)18, (byte)104};

        for (int i = 0; i < inputs.length; i++) {
            byte a = inputs[i][0];
            byte b = inputs[i][1];
            assertEquals(outputs[i], aes.gmul(a,b),  String.format("Gmul failed with input (%d, %d)",
                    a, b));
        }
    }

    @Test
    void subBytesTest() {
        AES aes = new AES();

        byte[][] inputs = new byte [][] { {24, 82, 57, 118, 90, (byte)150, 67, (byte)159, 122, 73, 36, (byte)242, 108, (byte)196, 40, (byte)212},
                {75, 105, 77, (byte)172, 118, (byte)247, (byte)128, 85, 54, 0, 61, (byte)223, (byte)128, 107, (byte)168, (byte)136}};

        byte[][] outputs = new byte [][] { {(byte)173, 0, 18, 56, (byte)190, (byte)144, 26, (byte)219, (byte)218, 59, 54, (byte)137, 80, 28, 52, 72},
                {(byte)179, (byte)249, (byte)227, (byte)145, 56, 104, (byte)205, (byte)252, 5, 99, 39, (byte)158, (byte)205, 127, (byte)194, (byte)196}};

        for (int i = 0; i < inputs.length; i++) {
            byte[] block = Arrays.copyOf(inputs[i], inputs[i].length);
            // Sub bytes
            aes.SubBytes(block);
            assertArrayEquals(outputs[i], block, String.format("Failed sub bytes step for input block %d", i));
        }


    }

    @Test
    void invSubBytesTest() {
        AES aes = new AES();

        byte[][] inputs = new byte [][] { {(byte)173, 0, 18, 56, (byte)190, (byte)144, 26, (byte)219, (byte)218, 59, 54, (byte)137, 80, 28, 52, 72},
                {(byte)179, (byte)249, (byte)227, (byte)145, 56, 104, (byte)205, (byte)252, 5, 99, 39, (byte)158, (byte)205, 127, (byte)194, (byte)196}};

        byte[][] outputs = new byte [][] { {24, 82, 57, 118, 90, (byte)150, 67, (byte)159, 122, 73, 36, (byte)242, 108, (byte)196, 40, (byte)212},
                {75, 105, 77, (byte)172, 118, (byte)247, (byte)128, 85, 54, 0, 61, (byte)223, (byte)128, 107, (byte)168, (byte)136}};

        for (int i = 0; i < inputs.length; i++) {
            byte[] block = Arrays.copyOf(inputs[i], inputs[i].length);

            // Inverse sub bytes
            aes.InvSubBytes(block);
            assertArrayEquals(outputs[i], block, String.format("Failed inverse sub bytes step for input block %d", i));
        }
    }

    @Test
    void fullSubBytesTest() {
        AES aes = new AES();

        // Sub and inverse tests
        for (int i = 0; i < 16; i++) {
            byte[] block = new byte[16];
            for (int b = 0; b < 16; b++) {
                block[b] = (byte)(i * 16 + b);
            }

            byte[] subblock = Arrays.copyOf(block, 16);
            aes.SubBytes(subblock);

            assertFalse(Arrays.equals(block,subblock), "SubBytes has had no effect.");

            aes.InvSubBytes(subblock);

            assertArrayEquals(block, subblock, "Application of subbytes and inverse subbytes did not return to original value");
        }
    }

    @Test
    void shiftRowsTest() {
        AES aes = new AES();

        byte[][] originalBlocks = new byte[][] {{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                { 17, 4, -57, -120, 0, 19, 30, 100, -40, -80, 12, 95, 110, 100, 99, 1 } };

        byte[][] shiftedBlocks = new byte[][] {{0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12, 1, 6, 11},
                {17, 19, 12, 1, 0, -80, 99, -120, -40, 100, -57, 100, 110, 4, 30, 95} };

        for (int i = 0; i < originalBlocks.length; i++)
        {
            byte[] currentBlock = Arrays.copyOf(originalBlocks[i], 16);
            aes.ShiftRows(currentBlock);
            assertArrayEquals(shiftedBlocks[i], currentBlock, "Unexpected output from shiftrows");
        }
    }

    @Test
    void invShiftRowsTest() {
        AES aes = new AES();

        byte[][] originalBlocks = new byte[][] {{0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12, 1, 6, 11},
                {17, 19, 12, 1, 0, -80, 99, -120, -40, 100, -57, 100, 110, 4, 30, 95} };

        byte[][] shiftedBlocks = new byte[][] {{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                { 17, 4, -57, -120, 0, 19, 30, 100, -40, -80, 12, 95, 110, 100, 99, 1 } };

        for (int i = 0; i < originalBlocks.length; i++)
        {
            byte[] currentBlock = Arrays.copyOf(originalBlocks[i], 16);
            aes.InvShiftRows(currentBlock);
            assertArrayEquals(shiftedBlocks[i], currentBlock, "Unexpected output from inverse shiftrows");
        }
    }

    @Test
    void fullShiftRowsTest() {
        AES aes = new AES();

        // Shift rows and inverse tests
        for (int i = 0; i < 16; i++) {
            byte[] block = new byte[16];
            for (int b = 0; b < 16; b++) {
                block[b] = (byte)(i * 16 + b);
            }

            byte[] subblock = Arrays.copyOf(block, 16);
            aes.ShiftRows(subblock);

            assertFalse(Arrays.equals(block,subblock), "Shift rows has had no effect.");

            aes.InvShiftRows(subblock);

            assertArrayEquals(block, subblock, "Application of Shiftrows and inverse Shiftrows did not return to original value");
        }
    }

    @Test
    void mixColsTest() {
        AES aes = new AES();

        byte[][] inputs = new byte [][]{
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                {0, 0, 0, 0, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 212, (byte) 212, (byte) 212, (byte) 213, 45, 38, 49, 76},
                {15, 85, (byte)141, 99, 102, 84, (byte)193, (byte)189, (byte)252, (byte)234, 102, 7, 102, 93, 110, 23}
        };

        byte[][] outputs = new byte [][] {
                {2, 7, 0, 5, 6, 3, 4, 1, 10, 15, 8, 13, 14, 11, 12, 9},
                {0, 0, 0, 0, (byte)255, (byte)255, (byte)255, (byte)255, (byte)213, (byte)213, (byte)215, (byte)214, 77, 126, (byte)189, (byte)248},
                {15, 74, (byte)254, 15, 76, 43, 119, 94, (byte)167, (byte)158, (byte)211, (byte)157, 82, 121, (byte)222, (byte)183}
        };

        for (int i = 0; i < inputs.length; i++) {
            byte[] block = Arrays.copyOf(inputs[i], 16);
            aes.MixColumns(block);
            assertArrayEquals(outputs[i], block, String.format("Output of mixcolumns was not correct for input %d", i));
        }
    }

    @Test
    void invMixColsTest() {
        AES aes = new AES();

        byte[][] inputs = new byte [][] {
                {2, 7, 0, 5, 6, 3, 4, 1, 10, 15, 8, 13, 14, 11, 12, 9},
                {0, 0, 0, 0, (byte)255, (byte)255, (byte)255, (byte)255, (byte)213, (byte)213, (byte)215, (byte)214, 77, 126, (byte)189, (byte)248},
                {15, 74, (byte)254, 15, 76, 43, 119, 94, (byte)167, (byte)158, (byte)211, (byte)157, 82, 121, (byte)222, (byte)183}
        };

        byte[][] outputs = new byte [][]{
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                {0, 0, 0, 0, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 212, (byte) 212, (byte) 212, (byte) 213, 45, 38, 49, 76},
                {15, 85, (byte)141, 99, 102, 84, (byte)193, (byte)189, (byte)252, (byte)234, 102, 7, 102, 93, 110, 23}
        };

        for (int i = 0; i < inputs.length; i++) {
            byte[] block = Arrays.copyOf(inputs[i], 16);
            aes.InvMixColumns(block);
            assertArrayEquals(outputs[i], block, String.format("Output of inverse mixcolumns was not correct for input %d", i));
        }
    }

    @Test
    void fullMixColsTest() {
        AES aes = new AES();

        // Shift rows and inverse tests
        for (int i = 0; i < 16; i++) {
            byte[] block = new byte[16];
            for (int b = 0; b < 16; b++) {
                block[b] = (byte)(i * 16 + b);
            }

            byte[] subblock = Arrays.copyOf(block, 16);
            aes.MixColumns(subblock);

            assertFalse(Arrays.equals(block,subblock), "Mix columns has had no effect.");

            aes.InvMixColumns(subblock);

            assertArrayEquals(block, subblock, "Application of MixColumns and inverse MixColumns did not return to original value");
        }
    }

    @Test
    void keyScheduleTest() {
        AES aes = new AES();

        byte[][] inputs = new byte [][]{
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0x49, 0x20, (byte)0xe2, (byte)0x99, (byte)0xa5, 0x20, 0x52, 0x61, 0x64, 0x69, 0x6f, 0x47, 0x61, 0x74, 0x75, 0x6e},
                {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}
        };

        byte[][] outputs = new byte [][]{
                {0x13, 0x11, 0x1D, 0x7F, (byte)0xE3, (byte)0x94, 0x4A, 0x17, (byte)0xF3, 0x07, (byte)0xA7, (byte)0x8B, 0x4D, 0x2B, 0x30, (byte)0xC5},
                {(byte)0xB4, (byte)0xEF, 0x5B, (byte)0xCB, 0x3E, (byte)0x92, (byte)0xE2, 0x11, 0x23, (byte)0xE9, 0x51, (byte)0xCF, 0x6F, (byte)0x8F, 0x18, (byte)0x8E},
                {0x75, 0x59, (byte)0x98, 0x71, 0x5B, 0x51, 0x2A, (byte)0xC0, (byte)0xF1, 0x5E, 0x4C, 0x5C, 0x6C, (byte)0xD0, (byte)0x85, (byte)0xF5},
                {(byte)0xD6, 0x0A, 0x35, (byte)0x88, (byte)0xE4, 0x72, (byte)0xF0, 0x7B, (byte)0x82, (byte)0xD2, (byte)0xD7, (byte)0x85, (byte)0x8C, (byte)0xD7, (byte)0xC3, 0x26}
        };

        for (int i = 0; i < inputs.length; i++) {
            byte[][] roundKeys = AESKeySchedule.generateRoundKeys(inputs[i], 11);
            byte[] lastKey = roundKeys[10];
            assertArrayEquals(outputs[i], lastKey, String.format("Last generated key in key schedule does not match for input %d", i));
        }
    }

    @Test
    void encryptTest() {
        AES aes = new AES();

        byte[] key = hexStringToByteArray("2b7e151628aed2a6abf7158809cf4f3c");

        String[] inputs = new String[] {
                "6bc1bee22e409f96e93d7e117393172a",
                "ae2d8a571e03ac9c9eb76fac45af8e51",
                "30c81c46a35ce411e5fbc1191a0a52ef",
                "f69f2445df4f9b17ad2b417be66c3710"
        };

        String[] outputs = new String[] {
                "3ad77bb40d7a3660a89ecaf32466ef97",
                "f5d3d58503b9699de785895a96fdbaaf",
                "43b1cd7f598ece23881b00e3ed030688",
                "7b0c785e27e8ad3f8223207104725dd4"
        };

        for (int i = 0; i < inputs.length; i++) {
            byte[] plaintextBlock = hexStringToByteArray(inputs[i]);
            byte[] targetCiphertext = hexStringToByteArray(outputs[i]);
            byte[] ciphertext = aes.encryptBlock(plaintextBlock, key);
            assertArrayEquals(targetCiphertext, ciphertext);
        }
    }

    @Test
    void decryptTest() {
        AES aes = new AES();

        byte[] key = hexStringToByteArray("2b7e151628aed2a6abf7158809cf4f3c");

        String[] inputs = new String[] {
                "3ad77bb40d7a3660a89ecaf32466ef97",
                "f5d3d58503b9699de785895a96fdbaaf",
                "43b1cd7f598ece23881b00e3ed030688",
                "7b0c785e27e8ad3f8223207104725dd4"
        };

        String[] outputs = new String[] {
                "6bc1bee22e409f96e93d7e117393172a",
                "ae2d8a571e03ac9c9eb76fac45af8e51",
                "30c81c46a35ce411e5fbc1191a0a52ef",
                "f69f2445df4f9b17ad2b417be66c3710"
        };

        for (int i = 0; i < inputs.length; i++) {
            byte[] ciphertextBlock = hexStringToByteArray(inputs[i]);
            byte[] targetPlaintext = hexStringToByteArray(outputs[i]);
            byte[] plaintext = aes.decryptBlock(ciphertextBlock, key);
            assertArrayEquals(targetPlaintext, plaintext);
        }
    }

    @Test
    void encryptDecryptTest() {
        AES aes = new AES();

        for (int k = 0; k < 10; k++) {
            byte[] key = randomBlock();
            for (int b = 0; b < 10; b++) {
                byte[] testBlock = randomBlock();
                byte[] ciphertext = aes.encryptBlock(testBlock, key);

                assertFalse(Arrays.equals(testBlock,ciphertext), "Plaintext and ciphertext are identical - no encryption seems to be occurring");

                byte[] decrypted = aes.decryptBlock(ciphertext, key);
                assertArrayEquals(testBlock, decrypted, "Encrypted and Decrypted blocks do not match");
            }
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] randomBlock() {
        byte[] block = new byte[16];
        rand.nextBytes(block);
        return block;
    }

}