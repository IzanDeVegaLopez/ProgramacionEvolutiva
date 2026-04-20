package selection_methods;

import Error.UnreachableCode;

public class ranking implements selection_method{
    public double pressure = 1.5;
    public tabla_frecuencias t;

    @Override
    public int[] chooseEntities(double[] fitness) throws Exception {
        int[] selected = new int[fitness.length];

        // In this array, an element with index n represents the rank of
        // fitness[n].
        int[] indexes_by_order = new int [fitness.length];
        for (int i = 0; i<fitness.length; i++) {
            double max = Double.NEGATIVE_INFINITY;
            int maxInd = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] > max) {
                    max = fitness[j];
                    maxInd = j;
                }
            }
            if(maxInd==-1) throw new UnreachableCode("Index of maximum was not set on ranking chooseEntities()");
            indexes_by_order[maxInd] = i;
            fitness[maxInd] = Double.MAX_VALUE;
        }

        double[] rates = new double[fitness.length];
        for (int i = 0; i< fitness.length;i++){
            int rank = indexes_by_order[i];
            rates[i] = 1.0/ fitness.length*(pressure-2*(pressure-1)*(rank-1)/(fitness.length-1));
        }

        t = new tabla_frecuencias(rates);
        for (int i = 0; i<selected.length; i++){
            float temp = (float)Math.random();
            int index = 0;
            while (t.frec_rel_acumulada[index] < temp) index++;
            selected[i] = index;
        }
        return selected;
    }
    @Override
    public double get_selection_enforcer() {
        return t.presion_selectiva;
    }
}
