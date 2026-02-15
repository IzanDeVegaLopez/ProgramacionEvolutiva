package elitism_methods;

import codification.*;

import java.util.ArrayList;

public class algorithm_member {
    public int fitness;
    public int penalty;
    public codificacion_binaria cod_bin;
    public codificacion_real cod_real;

    public algorithm_member(){
        fitness = 0;
        penalty = 0;
    }
    public algorithm_member(int f, int p, codificacion_binaria cb){
        fitness = f;
        penalty = p;
        cod_bin = cb;
    }
    public algorithm_member(int f, int p, codificacion_real cr){
        fitness = f;
        penalty = p;
        cod_real = cr;
    }
}
