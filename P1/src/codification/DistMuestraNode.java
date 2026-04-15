package codification;

public class DistMuestraNode extends ConditionalNode<DistMuestraNode>{
    public DistMuestraNode(int n){
        super(new SampleDist(n));
    }
}
