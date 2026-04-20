package selection_methods;

enum methods{
    estocastico,
    restos,
    ruleta,
    torneo,
    truncamiento,
    ranking
}
public interface selection_method {
    public abstract int[] chooseEntities(double[] fitness) throws Exception;
    public abstract double get_selection_enforcer();
}
