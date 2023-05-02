package uk.ac.nottingham.cryptography.symmetric;

import java.util.Arrays;

public class AES {

    public AES() { }

    public void SubBytes(byte[] block) {
        for (int i = 0; i < block.length; i++) {
            block[i] = AESConstants.AES_SBOX[block[i] & 0xFF];
        }
    }

    public void InvSubBytes(byte[] block) {
        for (int i = 0; i < block.length; i++) {
            block[i] = AESConstants.AES_INVSBOX[block[i] & 0xFF];
        }
    }

    public void ShiftRows(byte[] block) {
        byte temp = block[1];
        block[1] = block[5];
        block[5] = block[9];
        block[9] = block[13];
        block[13] = temp;

        temp = block[2];
        block[2] = block[10];
        block[10] = temp;
        temp = block[6];
        block[6] = block[14];
        block[14] = temp;

        temp = block[3];
        block[3] = block[15];
        block[15] = block[11];
        block[11] = block[7];
        block[7] = temp;
    }

    public void InvShiftRows(byte[] block) {
        byte temp = block[1];
        block[1] = block[13];
        block[13] = block[9];
        block[9] = block[5];
        block[5] = temp;

        temp = block[2];
        block[2] = block[10];
        block[10] = temp;
        temp = block[6];
        block[6] = block[14];
        block[14] = temp;

        temp = block[3];
        block[3] = block[7];
        block[7] = block[11];
        block[11] = block[15];
        block[15] = temp;
    }

    public byte gmul2(byte a) {
        if ((a & 0x80) > 0) {
            a = (byte) ((a << 1) ^ 0x1b);
        } else {
            a <<= 1;
        }
        return a;
    }

    public byte gmul3(byte a) {
        return (byte) (gmul2(a) ^ a);
    }

    public byte gmul(byte a, byte b) {
        byte[] temp = new byte[8];
        temp[0] = a;
        byte ans = 0;
        for(int i = 1; i < 8; i++) {
            temp[i] = gmul2(temp[i - 1]);
        }

        for (int i = 0; i < 8; i++) {
            byte bit = (byte) ((b >>> i) & 0x1);
            if (bit == 1) {
                ans ^= temp[i];
            }
        }
        return ans;
    }

    public void MixColumns(byte[] block) {
        for (int i = 0; i < block.length; i += 4) {
            byte[] output = new byte[4];
            output[0] = (byte) (gmul2(block[i]) ^ gmul3(block[i + 1]) ^ block[i + 2] ^ block[i + 3]);
            output[1] = (byte) (gmul2(block[i + 1]) ^ gmul3(block[i + 2]) ^ block[i + 3] ^ block[i]);
            output[2] = (byte) (gmul2(block[i + 2]) ^ gmul3(block[i + 3]) ^ block[i] ^ block[i + 1]);
            output[3] = (byte) (gmul2(block[i + 3]) ^ gmul3(block[i]) ^ block[i + 1] ^ block[i + 2]);
            block[i] = output[0];
            block[i + 1] = output[1];
            block[i + 2] = output[2];
            block[i + 3] = output[3];
        }
    }

    public void InvMixColumns(byte[] block) {
        for (int i = 0; i < 16; i += 4) {
            byte[] output = new byte[4];
            output[0] = (byte) (gmul(block[i], (byte) 14) ^ gmul(block[i + 1], (byte) 11) ^ gmul(block[i + 2], (byte) 13) ^ gmul(block[i + 3], (byte) 9));
            output[1] = (byte) (gmul(block[i], (byte) 9) ^ gmul(block[i + 1], (byte) 14) ^ gmul(block[i + 2], (byte) 11) ^ gmul(block[i + 3], (byte) 13));
            output[2] = (byte) (gmul(block[i], (byte) 13) ^ gmul(block[i + 1], (byte) 9) ^ gmul(block[i + 2], (byte) 14) ^ gmul(block[i + 3], (byte) 11));
            output[3] = (byte) (gmul(block[i], (byte) 11) ^ gmul(block[i + 1], (byte) 13) ^ gmul(block[i + 2], (byte) 9) ^ gmul(block[i + 3], (byte) 14));

            block[i] = output[0];
            block[i + 1] = output[1];
            block[i + 2] = output[2];
            block[i + 3] = output[3];
        }
    }

    public void AddKey(byte[] block, byte[] subkey) {
        for(int i = 0; i < block.length; i++) {
            block[i] = (byte) (block[i] ^ subkey[i]);
        }
    }

    public byte[] encryptBlock(byte[] messageBlock, byte[] key) {
        byte[][] subkeys = AESKeySchedule.generateRoundKeys(key, 11);
        byte[] cipherText = Arrays.copyOf(messageBlock, messageBlock.length);
        AddKey(cipherText, subkeys[0]);
        for (int i = 1; i < 10; i++) {
            SubBytes(cipherText);
            ShiftRows(cipherText);
            MixColumns(cipherText);
            AddKey(cipherText, subkeys[i]);
        }
        SubBytes(cipherText);
        ShiftRows(cipherText);
        AddKey(cipherText, subkeys[10]);

        return cipherText;
    }

    public byte[] decryptBlock(byte[] messageBlock, byte[] key) {
        byte[][] subkeys = AESKeySchedule.generateRoundKeys(key, 11);
        byte[] block = Arrays.copyOf(messageBlock, messageBlock.length);

        AddKey(block, subkeys[10]);
        InvShiftRows(block);
        InvSubBytes(block);

        for (int i = 9; i > 0; i--) {
            AddKey(block, subkeys[i]);
            InvMixColumns(block);
            InvShiftRows(block);
            InvSubBytes(block);
        }
        AddKey(block, subkeys[0]);

        return block;
    }

}
