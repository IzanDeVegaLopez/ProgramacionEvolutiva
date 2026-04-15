package mutation_methods;

import codification.IndividualCodification;

public interface BaseMutation {
    public void mutate(IndividualCodification cod) throws Exception;
}
