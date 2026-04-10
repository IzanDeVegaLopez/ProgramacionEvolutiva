package codification;

public class IndividualCodification  {
    public TreeNode node_tree;
    public void execute(RoverExecutionContext ctx) throws Exception{
        node_tree.execute_action(ctx);
    }
    public void impersonate(IndividualCodification other) throws Exception{
        node_tree = other.node_tree.get_deep_copy();
    }
}
