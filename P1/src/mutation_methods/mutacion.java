package mutation_methods;
import codification.*;

public class mutacion {
    static double ratio = 0.1f;
    public static void mutar(codificacion_binaria cod){
        for (int i = 0; i<cod.bitset.length(); i++)
            if (Math.random()<ratio) cod.bitset.flip(i);
    }

    public static void mutar(codificacion_real cod){
        for (int i = 0; i<cod.getNElems(); i++){
            for (int j = 0; j<cod.getSizeGenoma();j++){
                if (Math.random()<ratio) cod.mutate_at(i,j);
            }
        }
    }
}
