package codification;

import Error.UnreachableCode;

public class SandDist extends BooleanMethod{
    public SandDist(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_sand_dist() < value_compared;
    }
}
