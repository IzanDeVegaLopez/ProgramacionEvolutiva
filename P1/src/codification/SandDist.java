package codification;

import Error.UnreachableCode;

public class SandDist extends BooleanMethod<SandDist>{
    public SandDist(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_sand_dist() < value_compared;
    }

    public SandDist get_deep_copy() throws Exception{
        return new SandDist(value_compared);
    }
}
