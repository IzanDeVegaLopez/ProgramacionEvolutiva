package codification;

import Error.UnreachableCode;

public class BranchNode<T extends BranchNode<T>> implements TreeNode<T>{
    public TreeNode L_child;
    public TreeNode R_child;
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        throw new UnreachableCode("This function must be overriden by other node classes, cannot be called by itself");
    }
    @Override
    public boolean is_leaf() {
        return false;
    }
    @Override
    public T get_deep_copy() throws Exception {
        throw new UnreachableCode("This function cannot be called, it must always be one of the function that overrides this");
    }

    @Override
    public int get_n_childs(){
        return 2 + L_child.get_n_childs()+ R_child.get_n_childs();
    }
}