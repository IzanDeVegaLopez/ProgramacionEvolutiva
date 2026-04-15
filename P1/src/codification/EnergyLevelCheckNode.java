package codification;

public class EnergyLevelCheckNode extends ConditionalNode<EnergyLevelCheckNode>{
    public EnergyLevelCheckNode(int n){
        super(new EnergyLevelCheck(n));
    }
    public EnergyLevelCheckNode(BooleanMethod b){
        super(b);
    }
    @Override
    public EnergyLevelCheckNode get_deep_copy() throws Exception {
        return new EnergyLevelCheckNode(is_condition_true.get_deep_copy());
    }
}
