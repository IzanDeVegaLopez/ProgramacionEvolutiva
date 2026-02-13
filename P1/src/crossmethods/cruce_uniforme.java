package crossmethods;

import codification.codificacion_binaria;
import codification.codificacion_real;

public class cruce_uniforme implements bin_cross_base, float_cross_base{
    public void cross(codificacion_real cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i) {
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod.getSizeGenoma(); ++j){
                if(0.5 > Math.random())
                    cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }
    public void cross(codificacion_binaria cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i){
            //decide uno por uno si intercambiar genes
            for(int j = 0; j < cod.getSizeElem(); ++j){
                if(0.5 > Math.random())
                    cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }
    public void crossAll(codificacion_binaria[] cods, int codIdxA, int codIdxB) {
        int nBitsTotal =  (cods[codIdxA].getNElems()*cods[codIdxA].getSizeElem());
        for(int i = 0; i < nBitsTotal; ++i){
            if(Math.random() < 0.5) {
                boolean helper = cods[codIdxA].bitset.get(i);
                cods[codIdxA].bitset.set(i,cods[codIdxB].bitset.get(i));
                cods[codIdxB].bitset.set(i,helper);
            }
        }
    }
    public void crossAll(codificacion_real[] cods, int codIdxA, int codIdxB) {
        for(int i = 0; i < cods[codIdxA].floatVec.length; ++i){
            if(Math.random() < 0.5) {
                float helper = cods[codIdxA].floatVec[i];
                cods[codIdxA].floatVec[i] = cods[codIdxB].floatVec[i];
                cods[codIdxB].floatVec[i] = helper;
            }
        }
    }

}
