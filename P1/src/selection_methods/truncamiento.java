package selection_methods;

import java.util.Arrays;

public class truncamiento implements selection_method {
    double ratio = 0.5;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        int num_trunc = (int) Math.round(fitness.length*ratio);
        for (int i = 0; i<num_trunc; i++) {
            int max = -1;
            int maxInd = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] > max) {
                    max = fitness[j];
                    maxInd = j;
                }
            }
            selected[i] = maxInd;
            fitness[maxInd] = -1;
        }
        for (int i = num_trunc; i<fitness.length;i++){
            selected[i] = selected[i-num_trunc];
        }
        return selected;
    }
}
