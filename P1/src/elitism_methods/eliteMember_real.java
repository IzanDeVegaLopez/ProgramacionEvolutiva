package elitism_methods;

import codification.codificacion_real;

public class eliteMember_real {
    public int fitness;
    public codificacion_real member;
    public eliteMember_real(int f, codificacion_real cod){
        fitness = f;
        member = cod;
    }
}
