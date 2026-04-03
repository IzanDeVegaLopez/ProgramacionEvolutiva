package codification;

public class Generation {
    IndividualCodification all_individuals[];
    public enum randomization{
        RANDOMIZE
    }
    public Generation(int n_individuals){
        all_individuals = new IndividualCodification[n_individuals];
        for(int i = 0; i < n_individuals; ++i){
            all_individuals[i] = new IndividualCodification();
        }
    }
    public Generation(int n_individuals, randomization rand) throws Exception{
        all_individuals = new IndividualCodification[n_individuals];
        for(int i = 0; i < n_individuals; ++i){
            all_individuals[i] = new IndividualCodification();
            all_individuals[i].randomize();
        }
    }
}
