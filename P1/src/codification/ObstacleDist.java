package codification;

import Error.UnreachableCode;

public class ObstacleDist extends BooleanMethod<ObstacleDist>{
    public ObstacleDist(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_obstacle_dist() < value_compared;
    }

    public ObstacleDist get_deep_copy() throws Exception{
        return new ObstacleDist(value_compared);
    }
}
