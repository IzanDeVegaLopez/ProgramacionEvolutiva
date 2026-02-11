package selection_methods;
import codification.codificacion_binaria;
import codification.codificacion_real;
enum methods{
    estocastico,
    restos,
    ruleta,
    torneo,
    truncamiento
}
interface selection_method {
    public abstract int[] chooseEntities(int[] fitness);
    //returns array with index of each element chosen
}
