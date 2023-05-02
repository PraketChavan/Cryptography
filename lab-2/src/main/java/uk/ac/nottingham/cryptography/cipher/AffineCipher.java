package uk.ac.nottingham.cryptography.cipher;

public class AffineCipher implements HistoricCipher {

    private final int keyA;
    private final int keyB;
    private final int[] inverses = new int[] {0,1,0,9,0,21,0,15,0,3,0,19,0,0,0,7,0,23,0,11,0,5,0,17,0,25};

    public AffineCipher(int keyA, int keyB) throws Exception {
        if (inverses[keyA] == 0) throw new Exception();
        this.keyA = keyA;
        this.keyB = keyB % 26;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[] cipherText = new int[plaintext.length];
        for (int i = 0; i < plaintext.length; i++){
            cipherText[i] = (plaintext[i] * keyA + keyB) % 26;
        }
        return  cipherText;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[] plainText = new int[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            plainText[i] = (inverses[keyA] * (ciphertext[i] - keyB)) % 26;
            if (plainText[i] < 0) plainText[i] += 26;
        }
        return plainText;
    }
}
