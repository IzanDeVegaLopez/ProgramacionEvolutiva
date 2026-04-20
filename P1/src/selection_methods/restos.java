package selection_methods;

public class restos implements selection_method{
    methods metodo_restantes = methods.ruleta;
    selection_method aux_method;
    public tabla_frecuencias t;
    @Override
    public int[] chooseEntities(double[] fitness) throws Exception{
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias(fitness);
        int index = 0;
        double[] fit = fitness.clone();
        for (int i = 0; i< fit.length;i++){
            int temp = (int)(t.frec_rel[i] * fit.length);
            fit[i] = temp > 0 ? Double.POSITIVE_INFINITY : fitness[i];
            for (int j = 0; j<temp;j++){
                selected[index] = i;
                index++;
            }
        }
        if (index < fitness.length) {
            switch (metodo_restantes){
                case ruleta:
                    aux_method = new ruleta();
                    break;
                case estocastico:
                    aux_method = new estocastico();
                    break;
                case torneo:
                    aux_method = new torneo();
                    break;
                case truncamiento:
                    aux_method = new truncamiento();
                    break;
                case ranking:
                    aux_method = new ranking();
                    break;
            }
            int[] aux = aux_method.chooseEntities(fitness);
            int i = 0;
            while (index < fitness.length){
                selected[index] = aux[i];
                index++;
                i++;
            }
        }
        return selected;
    }

    @Override
    public double get_selection_enforcer() {
        return t.presion_selectiva;
    }
}
