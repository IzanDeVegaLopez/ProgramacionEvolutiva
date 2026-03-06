package mutation_methods;

import codification.codificacion_entera;

import java.util.BitSet;
import static utils.my_utils.get_random;
import static utils.GfG.nextPermutation;

public class heuristic_mutation implements mutation_base{
    int n_elems_to_select=3;
    public void mutate(codificacion_entera cod){
        BitSet already_chosen = new BitSet(cod.get_size());
        for(int i = 0; i < n_elems_to_select; ++i){
            int pos = get_random(cod.get_size());
            pos = already_chosen.nextClearBit(pos);
            if(pos > already_chosen.size()){
                pos = already_chosen.nextClearBit(0);
            }
            already_chosen.set(pos);
        }
        int[] chosen = new int[(int)already_chosen.stream().count()];
        int last_index = 0;
        int current_index = 0;
        while(last_index < chosen.length){
            chosen[current_index] = already_chosen.nextSetBit(last_index);
            last_index = chosen[current_index]+1;
            ++current_index;
        }

        //initialize permutations
        int[] current_permutation = new int[chosen.length];
        for(int i=0;i<current_permutation.length;++i)current_permutation[i]=i;

        //TODO: this should be equal to its fitness
        int best_fitness = 1000000;
        codificacion_entera best_cod = new codificacion_entera(cod.get_size());
        best_cod.copy(cod);
        for (int i = 0; i < (int)Math.pow(2,chosen.length); ++i){
            codificacion_entera my_cod = new codificacion_entera(cod.get_size());
            //build new codificacion binaria with current permutation
            int chosen_index = 0;
            for(int j = 0; j < cod.get_size(); ++j){
                if(chosen[current_permutation[chosen_index]]==j) {
                    my_cod.set_value(j, cod.get_value(chosen[current_permutation[chosen_index]]));
                }else{
                    my_cod.set_value(j, cod.get_value(j));
                }
            }

            //check and save last one
            //TODO: calculate fitness
            int new_fitness = 0;
            if(new_fitness < best_fitness){
                best_cod.copy(my_cod);
                best_fitness = new_fitness;
            }

            //get next permutation
            nextPermutation(current_permutation);
        }

        cod.copy(best_cod);
    }
}