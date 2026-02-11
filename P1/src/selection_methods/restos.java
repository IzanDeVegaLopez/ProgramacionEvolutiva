package selection_methods;

public class restos {
    methods metodo_restantes = methods.ruleta;
    selection_method aux_method;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        tabla_frecuencias t = new tabla_frecuencias(fitness);
        int index = 0;
        for (int i = 0; i< fitness.length;i++){
            int temp = (int)(t.frec_rel[i] * fitness.length);
            fitness[i] = temp > 0 ? -1 : fitness[i];
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
}
