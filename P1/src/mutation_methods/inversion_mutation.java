package mutation_methods;

import codification.codificacion_entera;

public class inversion_mutation implements mutation_base{
    public void mutate(codificacion_entera cod){
        //First position to swap
        int posA = (int)(Math.random()*cod.get_size());
        //Last position that will get swapped
        int posB = (int)(Math.random()*cod.get_size());

        if(posA>posB){
            posA += posB;
            posB = posA-posB;
            posA -= posB;
        }

        //Integer division
        int n_elems_to_interchange = (posB - posA)/2;
        for(int i = 0; i < n_elems_to_interchange; ++i){
            cod.swap(posA+i, posB-i);
        }
    }
}
