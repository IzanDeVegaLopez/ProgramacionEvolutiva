package selection_methods;

public class estocastico implements selection_method{
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        tabla_frecuencias t = new tabla_frecuencias(fitness);
        float separacion = 1.0f / fitness.length;
        float marca = (float)Math.random()*separacion;
        int index = 0;
        for (int i : selected){
            while (t.frec_rel_acumulada[index] < marca) index++;
            i = index;
            marca += separacion;
        }
        return selected;
    }
}
