package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;
import utils.my_utils;

public class random implements BaseMutation{
    public void mutate(IndividualCodification cod) throws Exception{
        int idx = my_utils.get_random(4);
        switch(idx){
            case 0 -> new terminal().mutate(cod);
            case 1 -> new funcional().mutate(cod);
            case 2 -> new subarbol().mutate(cod);
            case 3 -> new hoist().mutate(cod);
            default -> throw new UnreachableCode("Max value should be 4, and min 0. What happened? Result was:" + idx);
        }
    }
}