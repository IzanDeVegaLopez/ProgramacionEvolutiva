package codification;

public class EnergyLevelCheck extends BooleanMethod<EnergyLevelCheck>{
    public EnergyLevelCheck(int n){super(n);}
    @Override
    public boolean check_condition(RoverExecutionContext ctx) throws Exception{
        return ctx.get_energy_level() < value_compared;
    }

    public EnergyLevelCheck get_deep_copy() throws Exception{
        return new EnergyLevelCheck(value_compared);
    }
}
