package uk.ac.nottingham.cryptography.random;

public interface ShiftRegister {
    int clock();
    int getState();

    byte getByte();

}
