package codification;

import Error.UnreachableCode;

public class SampleDist extends BooleanMethod<SampleDist>{
    public SampleDist(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_sample_dist() < value_compared;
    }

    public SampleDist get_deep_copy() throws Exception{
        return new SampleDist(value_compared);
    }
}
