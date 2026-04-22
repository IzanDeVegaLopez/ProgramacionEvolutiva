package codification;

import Error.UnreachableCode;

public class Generation {
    public IndividualCodification[] all_individuals;

    private final static int min_depth = 1;

    public static enum randomization{
        RANDOMIZE
    }
    public Generation(int n_individuals)throws Exception{
        all_individuals = initialize_gen(n_individuals);
    }
    public IndividualCodification[] initialize_gen(int n_individuals) throws Exception{
        IndividualCodification[] gen = new IndividualCodification[n_individuals];
        for(int i = 0; i < gen.length; ++i){
            gen[i] = new IndividualCodification();
            gen[i].node_tree = NodeFactory.create_random_leaf_node_advance_double_prob();
        }
        return gen;
    }
    public Generation(int n_individuals, int max_depth, randomization rand) throws Exception{
        all_individuals = initialize_gen(n_individuals);
        switch(rand){
            case randomization.RANDOMIZE -> create_by_ramped_and_half(n_individuals, max_depth);
            default -> throw new UnreachableCode("How da fuck did ya reach here");
        }
    }

    public void create_by_ramped_and_half(int n_individuals, int max_depth) throws Exception{
        if(max_depth < min_depth)
            throw new UnreachableCode("Max depth is lesser than min depth");
        int individuos_por_nivel = n_individuals /(max_depth-min_depth+1);

        for(int profundidad = min_depth; profundidad <= max_depth; ++profundidad) {
            // HALF-AND-HALF: La mitad de este grupo será "Full" y la otra "Grow"
            for (int i = 0; i < individuos_por_nivel; ++i) {
                if (i <= individuos_por_nivel / 2) {
                    all_individuals[(profundidad-1)*individuos_por_nivel+i].node_tree = NodeFactory.create_random_complete_tree(0, profundidad);
                } else {
                    all_individuals[(profundidad-1)*individuos_por_nivel+i].node_tree = NodeFactory.create_random_grow_tree(0, profundidad);
                }
            }
        }
/*
        for(int i = min_depth; i <= max_depth; ++i){
            for(int j = 0; j < half_individuos_por_nivel; ++j){
                all_individuals[col*individuos_por_nivel+j].node_tree = NodeFactory.create_random_complete_tree(0, i);
            }
            for(int j = half_individuos_por_nivel; j < individuos_por_nivel; ++j){
                all_individuals[col*individuos_por_nivel+j].node_tree = NodeFactory.create_random_grow_tree(0, i);
            }
            ++col;
        }
*/
    }

    public void copy_individual(int idx, IndividualCodification cod) throws Exception{
        if(idx < 0 || idx >= all_individuals.length) throw new UnreachableCode("idx in copy_individual was out of range");

        all_individuals[idx].impersonate(cod);
    }
    public IndividualCodification get(int idx) throws Exception{
        if(idx < 0 || idx >= all_individuals.length) throw new UnreachableCode("idx in copy_individual was out of range");

        return all_individuals[idx];
    }
}
