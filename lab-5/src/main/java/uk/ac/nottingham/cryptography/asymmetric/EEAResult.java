package uk.ac.nottingham.cryptography.asymmetric;

public class EEAResult {
    private int r;
    private int s;
    private int t;

    public EEAResult(int r, int s, int t) {
        this.r = r;
        this.s = s;
        this.t = t;
    }

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

    public int getT() {
        return t;
    }

    @Override
    public String toString() {
        return  "r=" + r +
                ", s=" + s +
                ", t=" + t +
                '}';
    }
}
