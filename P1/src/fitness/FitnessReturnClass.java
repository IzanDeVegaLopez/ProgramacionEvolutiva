package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public int[] totalValue;
    public int best_value;
    public int mid;
    public FitnessReturnClass(int size){
        totalValue = new int[size];
        best_value = 0;
        mid = 0;
    }
}
