package codification;

//Executes first its left son, and second its right son
public class SequenceNode extends BranchNode<SequenceNode> {
    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        L_child.execute_action(ctx);
        R_child.execute_action(ctx);
    }
    @Override
    public void execute_action(RoverExecutionContext ctx, RoverExecutionContext.with_tiles t) throws Exception{
        L_child.execute_action(ctx, t);
        R_child.execute_action(ctx, t);
    }


    @Override
    public SequenceNode get_deep_copy() throws Exception{
        SequenceNode copy = new SequenceNode();
        copy.L_child = L_child.get_deep_copy();
        copy.R_child = R_child.get_deep_copy();
        return copy;
    }

    @Override
    public String write_me_down()throws Exception{
        return "Bloque do{\n" + L_child.write_me_down() + "\n and then \n"+R_child.write_me_down()+"\n}";
    }
}
