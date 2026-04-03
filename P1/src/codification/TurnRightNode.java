package codification;

public class TurnRightNode extends LeafNode<TurnRightNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        ctx.rotate(RoverExecutionContext.rotationDirection.RD_RIGHT);
    }
    @Override
    public TurnRightNode get_deep_copy() throws Exception{
        return new TurnRightNode();
    }
}
