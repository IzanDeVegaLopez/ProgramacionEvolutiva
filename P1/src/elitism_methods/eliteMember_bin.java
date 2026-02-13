package elitism_methods;

import codification.codificacion_binaria;

public class eliteMember_bin {
    public int fitness;
    public codificacion_binaria member;
    public eliteMember_bin(int f, codificacion_binaria cod){
        fitness = f;
        member = cod;
    }
}
