package mutation_methods;
import codification.*;

public class mutacion_inicial {
    static double ratio = 0.5f;
    public static void mutar_init(codificacion_binaria cod){
        for (int i = 0; i<cod.bitset.size(); i++)
            cod.bitset.set(i,Math.random()<ratio);
    }

    public static void mutar_init(codificacion_real cod){
        for (int i = 0; i<cod.getNElems(); i++){
            for (int j = 0; j<cod.getSizeGenoma();j++){
                cod.mutate_at(i,j);
            }
        }
    }
}
