package codification;

import java.util.function.Consumer;
import codification.BooleanMethod;

public class ConditionalNode<T extends ConditionalNode<T>> extends BranchNode<T>{
    BooleanMethod is_condition_true;

    ConditionalNode(BooleanMethod b){
        is_condition_true = b;
    }

    @Override
    public void execute_action(RoverExecutionContext ctx) throws Exception {
        if(is_condition_true.check_condition(ctx)){
            L_child.execute_action(ctx);
        }else{
            R_child.execute_action(ctx);
        }
    }
    @Override
    public void execute_action(RoverExecutionContext ctx, RoverExecutionContext.with_tiles t) throws Exception {
        if(is_condition_true.check_condition(ctx)){
            L_child.execute_action(ctx, t);
        }else{
            R_child.execute_action(ctx, t);
        }
    }
}
