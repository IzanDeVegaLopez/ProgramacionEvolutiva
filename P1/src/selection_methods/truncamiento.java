package selection_methods;

import java.util.Arrays;

public class truncamiento implements selection_method {
    double ratio = 0.5;
    public tabla_frecuencias_de_minimos t;
    public int[] chooseEntities(double[] fitness){
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias_de_minimos(fitness);
        double[] fit = fitness.clone();
        int num_trunc = (int) Math.round(fitness.length*ratio);
        for (int i = 0; i<num_trunc; i++) {
            double min = 100000;
            int minInd = -1;
            for (int j = 0; j < fit.length; j++) {
                if (fit[j] < min) {
                    min = fit[j];
                    minInd = j;
                }
            }
            selected[i] = minInd;
            fit[minInd] = Double.POSITIVE_INFINITY;
        }
        for (int i = num_trunc; i<fit.length;i++){
            selected[i] = selected[i-num_trunc];
        }
        return selected;
    }
    @Override
    public double get_selection_enforcer() {
        return t.presion_selectiva;
    }
}
