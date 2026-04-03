package codification;

public class IndividualCodification  {
    TreeNode node_tree;
    public void execute(RoverExecutionContext ctx) throws Exception{
        node_tree.execute_action(ctx);
    }
    public void randomize() throws Exception{
        NodeFactory.create_random_node_tree(0);
    }

    public void impersonate(IndividualCodification other) throws Exception{
        node_tree = other.node_tree.get_deep_copy();
    }
}
