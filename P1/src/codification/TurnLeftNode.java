package codification;

public class TurnLeftNode extends LeafNode<TurnLeftNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        ctx.rotate(RoverExecutionContext.rotationDirection.RD_LEFT);
    }
    @Override
    public void execute_action(RoverExecutionContext ctx, RoverExecutionContext.with_tiles t) throws Exception {
        ctx.rotate(RoverExecutionContext.rotationDirection.RD_LEFT, t);
    }
    @Override
    public TurnLeftNode get_deep_copy() throws Exception{
        return new TurnLeftNode();
    }
}
