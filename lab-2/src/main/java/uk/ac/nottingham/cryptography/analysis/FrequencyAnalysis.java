package uk.ac.nottingham.cryptography.analysis;

public class FrequencyAnalysis implements FitnessFunction {
    final double[] characterFrequencies = new double[]{0.080406052,
            0.014846488,
            0.033437737,
            0.038169583,
            0.124920625,
            0.024031234,
            0.018693758,
            0.050533014,
            0.075692775,
            0.001587737,
            0.005405135,
            0.040689861,
            0.025117606,
            0.072336292,
            0.076406929,
            0.02135891,
            0.001204689,
            0.062794207,
            0.065127666,
            0.092755648,
            0.027297018,
            0.010532516,
            0.016756642,
            0.002348569,
            0.016649801,
            0.000899507};

    @Override
    public double score(int[] text) {
        double likelihood = 1;
        for (int i: text) {
            likelihood *= characterFrequencies[i];
        }
        return likelihood;
    }
}
