package codification;

public class AdvanceNode extends LeafNode<AdvanceNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        ctx.advance();
    }
    @Override
    public void execute_action(RoverExecutionContext ctx, RoverExecutionContext.with_tiles t) throws Exception {
        ctx.advance(t);
    }

    @Override
    public AdvanceNode get_deep_copy() throws Exception{
        return new AdvanceNode();
    }

    @Override
    public String write_me_down()throws Exception{
        return "Advance";
    }
}
