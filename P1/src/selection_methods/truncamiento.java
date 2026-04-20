package selection_methods;

import java.util.Arrays;
import Error.UnreachableCode;

public class truncamiento implements selection_method {
    double ratio = 0.5;
    public tabla_frecuencias_de_minimos t;
    @Override
    public int[] chooseEntities(double[] fitness) throws Exception{
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias_de_minimos(fitness);
        double[] fit = fitness.clone();
        int num_trunc = (int) Math.round(fitness.length*ratio);
        for (int i = 0; i<num_trunc; i++) {
            double max = Double.NEGATIVE_INFINITY;
            int maxInd = -1;
            for (int j = 0; j < fit.length; j++) {
                if (fit[j] > max) {
                    max = fit[j];
                    maxInd = j;
                }
            }
            if(maxInd==-1) throw new UnreachableCode("Index of maximum was not set on ranking chooseEntities()");

            selected[i] = maxInd;
            fit[maxInd] = Double.POSITIVE_INFINITY;
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
