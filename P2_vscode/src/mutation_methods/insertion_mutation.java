package mutation_methods;

import codification.codificacion_entera;

public class insertion_mutation implements mutation_base{
    int number_of_insertions = 1;
    public void mutate(codificacion_entera cod){
        for(int i = 0; i < number_of_insertions; ++i){
            int element_to_displace_index = (int)(Math.random()*cod.get_size());
            int position_to_insert_element_in = (int)(Math.random()*cod.get_size());

            cod.insert(element_to_displace_index, position_to_insert_element_in);

        }
    }
}