package uk.ac.nottingham.cryptography;

import java.util.Arrays;

public class Main {
    public static void main(String args[]) {
        int x = rotater(rotatel(0x0FFFFF00, 28), 28);
        int y = bytesToInt(intToBytes(0xFAD14020));
        int[] output = (chachaQuaterRound(0x01020304, 0xAAABACAD, 0xFFFEFDFC, 0x04030201));
        int[] o1 = chachaQuaterRound(0x00000001, 0x00000002, 0x00000003, 0x00000004);
        int[] o2 = chachaQuaterRound(0x00224488, 0x00336699, 0x004488CC, 0x00000000);
        int[] o3 = chachaQuaterRound(0x01020304, 0x05060708, 0x090A0B0C, 0x0D0E0F10);

        System.out.println();
    }

    static int getBit(int x, int n) {
        return (x >> n) & 1;
    }

    static int zeroBit(int x, int n) {
        return x & ~(1 << n);
    }

    static int rotater(int x, int n) {
        int xr = x >>> n;
        int xl = x << (64 - n);
        return xr | xl;
    }

    static int rotatel(int x, int n) {
        int xr = x << n;
        int xl = x >>> (64 - n);
        return xr | xl;
    }

    static int bytesToInt(byte[] input) {
        int x = input[3] & 0xFF;
        for (int i = 2; i >= 0; i--) {
            x = (x << 8) | (input[i] & 0xFF);
        }
        return x;
    }

    static byte[] intToBytes(int x) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) (x & 0xFF);
            x = x >>> 8;
        }
        return bytes;
    }

    static int[] chachaQuaterRound(int a, int b, int c, int d) {
        a = a + b;
        d = a ^ d;
        d = rotatel(d, 16);
        c = c + d;
        b = b ^ c;
        b = rotatel(b, 12);
        a = a + b;
        d = d ^ a;
        d = rotatel(d, 8);
        c = c + d;
        b = b ^ c;
        b = rotatel(b, 7);
        return new int[] {a, b, c, d};
    }
}
