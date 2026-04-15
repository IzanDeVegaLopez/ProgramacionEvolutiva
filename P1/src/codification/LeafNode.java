package codification;

import Error.UnreachableCode;

public class LeafNode<T extends LeafNode<T>> implements TreeNode<T>{
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        throw new UnreachableCode("This function must be overriden by other node classes, cannot be called by itself");
    }

    @Override
    public boolean is_leaf() {
        return true;
    }

    @Override
    public T get_deep_copy() throws Exception{
        throw new UnreachableCode("This function cannot be called, it must always be one of the function that overrides this");
    }

    @Override
    public int get_n_childs(){
        return 0;
    }
}
