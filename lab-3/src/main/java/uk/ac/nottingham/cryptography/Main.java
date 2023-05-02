package uk.ac.nottingham.cryptography;

import uk.ac.nottingham.cryptography.random.LFSRX;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        LFSRX lfsx = new LFSRX(0b1001, new int[]{0, 1}, 4);
        HashSet<String> set = new HashSet<>();
        int i = 0;
        while(true) {
            i++;
            String binaryString = Integer.toBinaryString(lfsx.getState());
            byte ans = lfsx.getByte();
            System.out.println(ans);
            if (set.contains(binaryString)) break;
            else set.add(binaryString);
//            lfsx.clock();
        }
        System.out.println(i);
    }

}
