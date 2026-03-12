package mutation_methods;

import codification.codificacion_entera;

import java.util.BitSet;
import static utils.my_utils.get_random;
import static utils.next_permutation.nextPermutation;
import fitness.manhattan_distance_fitness_calculator;

public class heuristic_mutation implements mutation_base{
    int n_elems_to_select=3;
    manhattan_distance_fitness_calculator fit_calculator;
    public heuristic_mutation(manhattan_distance_fitness_calculator fit_calc){
        fit_calculator = fit_calc;
        factorial_result = factorial(n_elems_to_select);
    }
    int factorial_result;
    private int factorial(int n){
        ++n;
        int result = 1;
        for(int i = 2; i < n; ++i){
            result*=i;
        }
        return result;
    }
    public void mutate(codificacion_entera cod){
        BitSet already_chosen = new BitSet(cod.get_size());
        for(int i = 0; i < n_elems_to_select; ++i){
            int pos = get_random(cod.get_size());
            pos = already_chosen.nextClearBit(pos);
            if(pos >= cod.get_size()){
                pos = already_chosen.nextClearBit(0);
            }
            already_chosen.set(pos);
        }
        int[] chosen = new int[(int)already_chosen.stream().count()];
        int last_index = 0;
        int current_index = 0;
        while(current_index < chosen.length){
            chosen[current_index] = already_chosen.nextSetBit(last_index);
            last_index = chosen[current_index]+1;
            ++current_index;
        }

        //initialize permutations
        int[] current_permutation = new int[chosen.length];
        for(int i=0;i<current_permutation.length;++i)current_permutation[i]=i;

        //TODO: this should be equal to its fitness
        double best_fitness = 1000000;
        codificacion_entera best_cod = new codificacion_entera(cod.get_size());
        best_cod.copy(cod);
        for (int i = 0; i < factorial_result; ++i){
            codificacion_entera my_cod = new codificacion_entera(cod.get_size());
            //build new codificacion binaria with current permutation
            int chosen_index = 0;
            for(int j = 0; j < cod.get_size(); ++j){
                if(chosen_index < chosen.length && chosen[chosen_index]==j) {
                    my_cod.set_value(j, cod.get_value(chosen[current_permutation[chosen_index]]));
                    ++chosen_index;
                }else{
                    my_cod.set_value(j, cod.get_value(j));
                }
            }

            //check and save last one
            //TODO: calculate fitness
            double new_fitness = fit_calculator.calculate_one_codification_fitness(my_cod).value;
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