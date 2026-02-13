package selection_methods;

public class torneo implements selection_method{
    int muestra = 3;
    public tabla_frecuencias t;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        /// Contiene los *índices* de los elementos seleccionados.
        int[] muestra_seleccionada = new int[muestra];
        t = new tabla_frecuencias(fitness);
        for (int i = 0; i<selected.length; i++) {
            for (int j = 1; j<muestra;j++) {
                muestra_seleccionada[j] = (int) (Math.random() * fitness.length);
            }
            selected[i] = muestra_seleccionada[0];
            for (int j = 1; j<muestra;j++){
                selected[i] = fitness[selected[i]] >= fitness[muestra_seleccionada[j]]
                        ? selected[i] : muestra_seleccionada[j];
            }
        }
        return selected;
    }
}
