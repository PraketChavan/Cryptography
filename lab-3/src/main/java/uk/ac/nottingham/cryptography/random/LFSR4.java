package uk.ac.nottingham.cryptography.random;

public class LFSR4 implements ShiftRegister {

    int state;

    public LFSR4(int initialState) {
        state = initialState & 0b1111;
    }

    @Override
    public int clock() {
        int output = this.state & 0b1;
        int secondLast = this.state >>> 1 & 0b1;
        this.state >>>= 1;
        this.state = this.state | (output ^ secondLast) << 3;
        return output;
    }

    @Override
    public int getState() {
        // TODO
        return state;
    }

    @Override
    public byte getByte() {
        return 0;
    }

}
