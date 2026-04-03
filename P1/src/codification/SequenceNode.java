package codification;

//Executes first its left son, and second its right son
public class SequenceNode extends BranchNode<SequenceNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        L_child.execute_action(ctx);
        R_child.execute_action(ctx);
    }

    @Override
    public SequenceNode get_deep_copy() throws Exception{
        SequenceNode copy = new SequenceNode();
        copy.L_child = L_child.get_deep_copy();
        copy.R_child = R_child.get_deep_copy();
        return copy;
    }
}
