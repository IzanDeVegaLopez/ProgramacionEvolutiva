package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;

public class terminal implements BaseMutation{
    public void Mutate(IndividualCodification cod) throws Exception{
        throw new UnreachableCode("Unimplemented");
    }
}