package selection_methods;

public class ranking implements selection_method{
    public double pressure = 1.0;
    public tabla_frecuencias t;
    public int[] chooseEntities(double[] fitness) {
        int[] selected = new int[fitness.length];

        // In this array, an element with index n represents the rank of
        // fitness[n].
        int[] indexes_by_order = new int [fitness.length];
        for (int i = 0; i<fitness.length; i++) {
            double max = -1;
            int maxInd = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] > max) {
                    max = fitness[j];
                    maxInd = j;
                }
            }
            indexes_by_order[maxInd] = i;
            fitness[maxInd] = -1;
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
}
