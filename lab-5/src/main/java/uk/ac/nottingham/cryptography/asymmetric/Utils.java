package uk.ac.nottingham.cryptography.asymmetric;

public class Utils {

    private Utils () {}

    public static int euclideanAlgorithm (int r0, int r1) {
        if (r1 > r0) {
            int temp = r1;
            r1 = r0;
            r0 = temp;
        }
        while (r1 != 0) {
            r0 = r0 % r1;
            int temp = r0;
            r0 = r1;
            r1 = temp;
        }
        return r0;
    }

    public static EEAResult extendedEuclideanAlgorithm(int r0, int r1) {
        int r2, s2, t2;
        boolean flipped = false;
        if (r1 > r0) {
            int temp = r1;
            r1 = r0;
            r0 = temp;
            flipped = true;
        }
        int s0 = 1, s1 = 0, t0 = 0, t1 = 1;
        while (r1 != 0) {
            r2 = r0 % r1;
            int q = (r0 - r2) / r1;
            s2 = (s0 - q * s1);
            t2 = (t0 - q * t1);

            r0 = r1;
            r1 = r2;

            s0 = s1;
            s1 = s2;

            t0 = t1;
            t1 = t2;
        }

        return flipped ? new EEAResult(r0, t0, s0) : new EEAResult(r0,s0,t0);
    }

    public static int multiplicativeInverse(int a, int n) {
        EEAResult result = extendedEuclideanAlgorithm(a, n);
        return (result.getT() + n) % n;
    }

    public static int modPow(int a, int e, int n) {
        long ans = 1;
        for (int i = Integer.toBinaryString(e).length() - 1; i >= 0 ; i--) {
            int bit = e >>> i & 0x1;
            ans = (ans * ans) % n;
            if (bit == 1) {
                ans = ans * a % n;
            }
        }
        return (int) (ans % n);
    }
}
