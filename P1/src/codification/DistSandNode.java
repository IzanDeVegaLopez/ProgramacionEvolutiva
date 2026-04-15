package codification;

import Error.UnreachableCode;

public class DistSandNode extends ConditionalNode<DistSandNode>{
    public DistSandNode(int n){
        super(new SandDist(n));
    }
    public DistSandNode(BooleanMethod b){
        super(b);
    }
    @Override
    public DistSandNode get_deep_copy() throws Exception {
        return new DistSandNode(is_condition_true.get_deep_copy());
    }
}
