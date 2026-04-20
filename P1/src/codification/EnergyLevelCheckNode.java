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
        EnergyLevelCheckNode node = new EnergyLevelCheckNode(is_condition_true.get_deep_copy());
        node.L_child = L_child.get_deep_copy();
        node.R_child = R_child.get_deep_copy();
        return node;
    }
}
