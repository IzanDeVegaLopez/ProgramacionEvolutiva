package codification;

import Error.UnreachableCode;

public class BooleanMethod {
    int value_compared;
    public BooleanMethod(int value){
        value_compared = value;
    }
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        throw new UnreachableCode("unimplemented");
    }
}
