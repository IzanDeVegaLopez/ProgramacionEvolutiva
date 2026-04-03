package codification;

public class AdvanceNode extends LeafNode<AdvanceNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        ctx.advance();
    }
    @Override
    public AdvanceNode get_deep_copy() throws Exception{
        return new AdvanceNode();
    }
}
