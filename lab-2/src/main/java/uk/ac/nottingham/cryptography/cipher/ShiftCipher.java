package uk.ac.nottingham.cryptography.cipher;

public class ShiftCipher implements HistoricCipher {

    int key = 0;

    public ShiftCipher(int key) {
        this.key = key;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[] cipherText = new int[plaintext.length];
        for (int i = 0; i < plaintext.length; i++) {
            cipherText[i] = (plaintext[i] + key) % 26;
        }
        return cipherText;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[] plainText = new int[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            plainText[i] = (ciphertext[i] - key) % 26;
            if (plainText[i] < 0) plainText[i] += 26;
        }
        return plainText;
    }



}
