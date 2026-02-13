package selection_methods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class ruleta implements selection_method{
    public tabla_frecuencias t;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias(fitness);
        for (int i = 0; i<selected.length; i++){
            float temp = (float)Math.random();
            int index = 0;
            while (t.frec_rel_acumulada[index] < temp) index++;
            selected[i] = index;
        }
        return selected;
    }
}
