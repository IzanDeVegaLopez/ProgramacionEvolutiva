import codification.codificacion_binaria;
import codification.codificacion_real;

package selection_method;

interface bin_selection_method {
    public abstract int[] chooseEntities(codificacion_binaria[] cod);
    //returns array with index of each element chosen
}

interface float_selection_method {
    public abstract int[] chooseEntities(codificacion_real[] cod);
}
