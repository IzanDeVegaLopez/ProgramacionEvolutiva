package codification;

public class IndividualCodification  {
    public TreeNode node_tree;
    public void execute(RoverExecutionContext ctx) throws Exception{
        node_tree.execute_action(ctx);
    }
    public void execute(RoverExecutionContext ctx, RoverExecutionContext.with_tiles t) throws Exception{
        node_tree.execute_action(ctx, t);
    }
    public void impersonate(IndividualCodification other) throws Exception{
        node_tree = other.node_tree.get_deep_copy();
    }
    public void impersonate(TreeNode other) throws Exception{
        node_tree = other.get_deep_copy();
    }
    public int get_number_of_child_nodes(){
        return node_tree.get_n_childs();
    }
}
