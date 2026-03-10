package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public double[] totalValue;
    public double best_value;
    public double mid;
    public FitnessReturnClass(int size){
        totalValue = new double[size];
        best_value = 0;
        mid = 0;
    }
}
