package selection_methods;
import codification.codificacion_binaria;
import codification.codificacion_real;

interface selection_method {
    public abstract int[] chooseEntities(int[] fitness, int count);
    //returns array with index of each element chosen
}
