package selection_methods;

public class ranking implements selection_method{
    public double pressure = 1.5;
    public tabla_frecuencias t;
    public int[] chooseEntities(double[] fitness) {
        int[] selected = new int[fitness.length];

        // In this array, an element with index n represents the rank of
        // fitness[n].
        int[] indexes_by_order = new int [fitness.length];
        for (int i = 0; i<fitness.length; i++) {
            double min = Double.MAX_VALUE;
            int minInd = Integer.MAX_VALUE;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] < min) {
                    min = fitness[j];
                    minInd = j;
                }
            }
            indexes_by_order[minInd] = i;
            fitness[minInd] = Double.MAX_VALUE;
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
