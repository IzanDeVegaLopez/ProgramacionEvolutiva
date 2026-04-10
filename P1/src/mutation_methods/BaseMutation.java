package mutation_methods;

import codification.IndividualCodification;

public interface BaseMutation {
    public void Mutate(IndividualCodification cod) throws Exception;
}
