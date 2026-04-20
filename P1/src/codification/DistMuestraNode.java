package codification;

public class DistMuestraNode extends ConditionalNode<DistMuestraNode>{
    public DistMuestraNode(int n){
        super(new SampleDist(n));
    }
    public DistMuestraNode(BooleanMethod b){
        super(b);
    }
    @Override
    public DistMuestraNode get_deep_copy() throws Exception {
        DistMuestraNode node = new DistMuestraNode(is_condition_true.get_deep_copy());
        node.L_child = L_child.get_deep_copy();
        node.R_child = R_child.get_deep_copy();
        return node;
    }
}
