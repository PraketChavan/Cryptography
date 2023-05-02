package uk.ac.nottingham.cryptography.cipher;

public interface HistoricCipher {
    int[] encrypt(int[] plaintext);
    int[] decrypt(int[] ciphertext);
}
