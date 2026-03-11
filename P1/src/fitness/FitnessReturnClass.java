package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public double[] totalValue;
    public double mid;
    public fitness_return_type best_fitness_result;
    public int best_codification_index = -1;
    public FitnessReturnClass(int size){
        totalValue = new double[size];
        mid = 0;
        best_fitness_result = new fitness_return_type();
    }
}
