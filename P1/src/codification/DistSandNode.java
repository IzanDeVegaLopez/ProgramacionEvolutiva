package codification;

import Error.UnreachableCode;

public class DistSandNode extends ConditionalNode<DistSandNode>{
    public DistSandNode(int n){
        super(new SandDist(n));
    }
    @Override
    public DistSandNode get_deep_copy() throws Exception {
        throw new UnreachableCode("This function cannot be called, it must always be one of the function that overrides this");
    }
}
