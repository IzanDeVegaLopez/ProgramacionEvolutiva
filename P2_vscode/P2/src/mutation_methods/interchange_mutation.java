package mutation_methods;

import codification.codificacion_entera;

public class interchange_mutation  implements mutation_base{
    public void mutate(codificacion_entera cod){
        if(cod.get_size() < 2) return;

        int posA = (int) (Math.random() * cod.get_size());
        int posB = (int) (Math.random() * cod.get_size());
        cod.swap(posA, posB);

    }
}
