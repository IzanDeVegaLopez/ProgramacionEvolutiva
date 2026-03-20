package mutation_methods;

import codification.codificacion_entera;

import java.util.Arrays;
import java.util.BitSet;

import static utils.my_utils.get_random;

public class rock_paper_scissors_mutation implements mutation_base{
    int n_elems_to_select = 3;
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

        // 0 R
        // 1 P
        // 2 S

        // num1 - num2
        // 0 - empate (gana 2)
        // 1 - gana 2
        // 2 - gana 1
        for (int i = 0; i<n_elems_to_select-1;i++){
            int play_1 = get_random(3);
            int play_2 = get_random(3);
            int result = (play_1-play_2+3)%3;
            if (result<2) cod.swap(chosen[i], chosen[i+1]);
        }
    }
}
