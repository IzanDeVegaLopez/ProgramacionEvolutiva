package codification;

import Error.UnreachableCode;

public class BooleanMethod<T extends BooleanMethod<T>> {
    int value_compared;
    public BooleanMethod(int value){
        value_compared = value;
    }
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        throw new UnreachableCode("unimplemented");
    }

    public T get_deep_copy() throws Exception{
        throw new UnreachableCode("Unimplemented");
    }
}
