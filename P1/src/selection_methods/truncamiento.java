package selection_methods;

import java.util.Arrays;

public class truncamiento implements selection_method {
    double ratio = 0.5;
    public tabla_frecuencias_de_minimos t;
    public int[] chooseEntities(double[] fitness){
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias_de_minimos(fitness);
        int num_trunc = (int) Math.round(fitness.length*ratio);
        for (int i = 0; i<num_trunc; i++) {
            double min = 100000;
            int minInd = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] < min) {
                    min = fitness[j];
                    minInd = j;
                }
            }
            selected[i] = minInd;
            fitness[minInd] = -1;
        }
        for (int i = num_trunc; i<fitness.length;i++){
            selected[i] = selected[i-num_trunc];
        }
        return selected;
    }
}
