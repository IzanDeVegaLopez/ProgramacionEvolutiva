package crossmethods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class cruce_multipunto implements bin_cross_base, float_cross_base{
    public float probability = 0.3f;
    public void cross(codificacion_real cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i) {
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod.getSizeGenoma(); ++j){
                if(probability > Math.random())
                    cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }
    public void cross(codificacion_binaria cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i){
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod.getSizeElem(); ++j){
                if(probability > Math.random())
                    cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }
}
