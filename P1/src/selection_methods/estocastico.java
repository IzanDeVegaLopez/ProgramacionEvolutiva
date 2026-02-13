package selection_methods;

public class estocastico implements selection_method{
    public tabla_frecuencias t;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias(fitness);
        float separacion = 1.0f / fitness.length;
        float marca = (float)Math.random()*separacion;
        int index = 0;
        for (int i = 0; i<selected.length; i++){
            while (t.frec_rel_acumulada[index] < marca) index++;
            selected[i] = index;
            marca += separacion;
        }
        return selected;
    }
}
