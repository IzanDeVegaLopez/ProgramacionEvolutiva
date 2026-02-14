package mutation_methods;
import codification.*;

public class mutacion_a_nivel_de_gen {
    double ratio;
    public mutacion_a_nivel_de_gen(double chance){
        ratio = chance;
    }
    public void mutar(codificacion_binaria cod){
        for (int i = 0; i<cod.bitset.size(); i++)
            if (Math.random()<ratio) cod.bitset.flip(i);
    }

    public void mutar(codificacion_real cod){
        for (int i = 0; i<cod.floatVec.length; i++){
            if (Math.random()<ratio) cod.mutate_at(i);
        }
    }
}
