package selection_methods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class ruleta implements selection_method{
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        tabla_frecuencias t = new tabla_frecuencias(fitness);
        for (int i : selected){
            float temp = (float)Math.random();
            int index = 0;
            while (t.frec_rel_acumulada[index] < temp) index++;
            i = index;
        }
        return selected;
    }
}
