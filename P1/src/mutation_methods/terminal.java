package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;

public class terminal implements BaseMutation{
    public void mutate(IndividualCodification cod) throws Exception{
        throw new UnreachableCode("Unimplemented");
    }
}