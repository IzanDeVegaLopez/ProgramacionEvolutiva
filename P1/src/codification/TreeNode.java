package codification;

public interface TreeNode<T extends TreeNode<T>> {
    public void execute_action(RoverExecutionContext ctx) throws Exception;
    public boolean is_leaf();
    public T get_deep_copy() throws Exception;
    public int get_n_childs();
}
