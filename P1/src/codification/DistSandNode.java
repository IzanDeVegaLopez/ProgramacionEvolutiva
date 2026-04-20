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

    @Override
    public String write_me_down()throws Exception{
        return "if Dist to Sand < "+ is_condition_true.value_compared +" do{\n" + L_child.write_me_down() + "\n} else { \n"+R_child.write_me_down()+"\n}";
    }
}
