package uk.ac.nottingham.cryptography;

import uk.ac.nottingham.cryptography.analysis.FrequencyAnalysis;
import uk.ac.nottingham.cryptography.cipher.AffineCipher;
import uk.ac.nottingham.cryptography.cipher.ShiftCipher;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        String s = "DLBURJTUOTYQBTSVQQREMUOTNULGDTEMNDLBPRJTVEDLBGYTMR" +
                "EMYTQVTITPORUTITGDLBPREUULYTQVTITDLBURJTUOTGTMSVQQ"+
               "REMDLBNURDVEPLEMTGQREMREMVNOLPDLBOLPMTTSUOTGRYYVUO" +
                "LQTHLTN";

        double bestScore = Double.MIN_VALUE;
        int bestKeyA = 0;
        int bestKeyB = 0;
        FrequencyAnalysis a = new FrequencyAnalysis();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                try {
                    AffineCipher cipher = new AffineCipher(j, i);
                    int[] plainText = cipher.decrypt(StringToInts(s));
                    double score = a.score(plainText);
                    if (score > bestScore) {
                        bestScore = score;
                        bestKeyA = j;
                        bestKeyB = i;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        System.out.println(IntsToString(new AffineCipher(bestKeyA, bestKeyB).decrypt(StringToInts(s))));
    }

    public static int[] StringToInts(String input) {
        input = input.toLowerCase();
        int[] bytes = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            bytes[i] = input.charAt(i) - 'a';
        }
        return bytes;
    }
    public static String IntsToString(int[] input) {
        StringBuilder str = new StringBuilder();
        for(int i : input) {
            str.append(((char) ('a' + i)));
        }
        return str.toString();
    }
}
