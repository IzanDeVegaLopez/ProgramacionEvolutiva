package selection_methods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class ruleta implements selection_method{
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        tabla_frecuencias t = new tabla_frecuencias(fitness);
        for (int i = 0; i<fitness.length;i++){
            float temp = (float)Math.random();
            int index = 0;
            while (t.frec_rel_acumulada[index] < temp) index++;
            selected[i] = index;
        }
        return selected;
    }
}
