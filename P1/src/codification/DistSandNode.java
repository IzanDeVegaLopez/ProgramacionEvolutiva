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
        DistSandNode node = new DistSandNode(is_condition_true.get_deep_copy());
        node.L_child = L_child.get_deep_copy();
        node.R_child = R_child.get_deep_copy();
        return node;
    }
}
