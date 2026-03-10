package selection_methods;

enum methods{
    estocastico,
    restos,
    ruleta,
    torneo,
    truncamiento
}
public interface selection_method {
    public float selectionEnforcer=0;
    public abstract int[] chooseEntities(double[] fitness);
    //returns array with index of each element chosen
}
