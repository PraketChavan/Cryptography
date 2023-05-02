package uk.ac.nottingham.cryptography.random;

public class LFSRX implements ShiftRegister{

    int state;
    int[] polynomials;
    int length;

    public LFSRX(int state, int[] polynomials, int length) {
        this.state = state;
        this.polynomials = polynomials;
        this.length = length;
    }

    @Override
    public int clock() {
        int output = this.state & 0b1;
        int feedback = 0;
        for (int tap: polynomials) {
            feedback ^= this.state >>> tap & 0b1;
        }
        this.state >>>= 1;
        this.state |= feedback << (length - 1);
        return output;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public byte getByte() {.
        byte ans = 0b0;
        for (int i = 0; i < 8; i++) {
            ans |= this.clock() << i;
        }
        return ans;
    }
}
