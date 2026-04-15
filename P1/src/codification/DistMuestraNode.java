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
        return new DistMuestraNode(is_condition_true.get_deep_copy());
    }
}
