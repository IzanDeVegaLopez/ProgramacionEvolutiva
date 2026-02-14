package GeneticAlgorithm;

import fitness.FitnessReturnClass;

public class GeneticAlgorithmBase {
    FitnessReturnClass bestSol;
    float midSelectionEnforcer;
    public float[] getMidSelectionEnforcer_n_getMax(){
        return new float[]{midSelectionEnforcer, bestSol.totalValue};
    }
}
