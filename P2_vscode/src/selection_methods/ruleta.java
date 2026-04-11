package selection_methods;

public class ruleta implements selection_method{
    public tabla_frecuencias_de_minimos t;
    public int[] chooseEntities(double[] fitness){
        int[] selected = new int[fitness.length];
        t = new tabla_frecuencias_de_minimos(fitness);
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
