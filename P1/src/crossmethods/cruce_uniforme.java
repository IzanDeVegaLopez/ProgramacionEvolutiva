package crossmethods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class cruce_uniforme implements bin_cross_base, float_cross_base{
    public float probability = 0.3f;
    public void cross(codificacion_real[] cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod[0].getNElems(); ++i) {
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod[0].getSizeGenoma(); ++j){
                if(probability > Math.random())
                    cod[0].swap(elemIdxA,elemIdxB,j);
            }
        }
    }
    public void cross(codificacion_binaria[] cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod[0].getNElems(); ++i){
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod[0].getSizeElem(); ++j){
                if(probability > Math.random())
                    cod[0].swap(elemIdxA,elemIdxB,j);
            }
        }
    }
}
