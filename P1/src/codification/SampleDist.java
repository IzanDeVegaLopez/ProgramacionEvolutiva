package codification;

import Error.UnreachableCode;

public class SampleDist extends BooleanMethod{
    public SampleDist(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_sample_dist() < value_compared;
    }
}
