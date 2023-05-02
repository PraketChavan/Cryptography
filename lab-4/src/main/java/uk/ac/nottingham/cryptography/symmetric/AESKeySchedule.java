package uk.ac.nottingham.cryptography.symmetric;

import java.util.Arrays;
import uk.ac.nottingham.cryptography.symmetric.AESConstants;

public class AESKeySchedule {

    private AESKeySchedule() {}

    private static void CalculateG(byte[] g, byte[] block, int word, byte rci) {
        g[0] = (byte)(AESConstants.AES_SBOX[block[word * 4 + 1] & 0xff] ^ rci);
        g[1] = AESConstants.AES_SBOX[block[word * 4 + 2] & 0xff];
        g[2] = AESConstants.AES_SBOX[block[word * 4 + 3] & 0xff];
        g[3] = AESConstants.AES_SBOX[block[word * 4] & 0xff];
    }

    private static void keyWordXor(byte[] a, int wa, byte[] b, int wb, byte[] c, int wc ) {
        wa *= 4;
        wb *= 4;
        wc *= 4;

        c[wc] = (byte)(a[wa] ^ b[wb]);
        c[wc+1] = (byte)(a[wa+1] ^ b[wb+1]);
        c[wc+2] = (byte)(a[wa+2] ^ b[wb+2]);
        c[wc+3] = (byte)(a[wa+3] ^ b[wb+3]);
    }

    public static byte[][] generateRoundKeys(byte[] k, int n) {
        byte[][] roundKeys = new byte[n][];
        byte[] g = new byte[4];
        roundKeys[0] = Arrays.copyOf(k, k.length);

        for (int i = 1; i < n; i++) {
            byte[] prevRoundKey = roundKeys[i - 1];
            byte[] nextkey = new byte[16];

            CalculateG(g, prevRoundKey, 3, AESConstants.AES_RCON[i]);
            keyWordXor(prevRoundKey, 0, g, 0, nextkey, 0);
            keyWordXor(prevRoundKey, 1, nextkey, 0, nextkey, 1);
            keyWordXor(prevRoundKey, 2, nextkey, 1, nextkey, 2);
            keyWordXor(prevRoundKey, 3, nextkey, 2, nextkey, 3);

            roundKeys[i] = nextkey;
        }

        return roundKeys;
    }

}
