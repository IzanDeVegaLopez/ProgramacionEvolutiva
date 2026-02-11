package selection_methods;

public class torneo implements selection_method{
    int muestra = 3;
    public int[] chooseEntities(int[] fitness){
        int[] selected = new int[fitness.length];
        /// Contiene los *índices* de los elementos seleccionados.
        int[] muestra_seleccionada = new int[muestra];
        for (int s : selected) {
            for (int m : muestra_seleccionada) {
                m = (int) (Math.random() * fitness.length);
            }
            s = muestra_seleccionada[0];
            for (int i = 1; i<muestra;i++){
                s = fitness[s] >= fitness[muestra_seleccionada[i]]
                        ? s : muestra_seleccionada[i];
            }
        }
        return selected;
    }
}
