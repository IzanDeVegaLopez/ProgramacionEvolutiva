package crossmethods;

import codification.*;

public class cruce_monopunto implements bin_cross_base, float_cross_base{
    public void cross(codificacion_real cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i) {
            int temp = (int)Math.floor(Math.random() * (cod.getSizeGenoma()-1));
            temp+=1;

            //intercambia los bits desde 0 hasta el número en temp de ambos elementos
            for(int j = 0; j < temp; ++j){
                cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }

    public void cross(codificacion_binaria cod, int elemIdxA, int elemIdxB){
        for(int i = 0; i < cod.getNElems(); ++i){
            //Para no hacer divisiones obvias antes del primero o después del último
            int temp = (int)Math.floor(Math.random() * (cod.getSizeElem()-1));
            temp+=1;

            //intercambia los bits desde 0 hasta el número en temp de ambos elementos
            for(int j = 0; j < temp; ++j){
                cod.swap(elemIdxA,elemIdxB,j);
            }
        }
    }
    public void crossAll(codificacion_binaria[] cods, int codIdxA, int codIdxB){
        int nBitsTotal =  (cods[codIdxA].getNElems()*cods[codIdxA].getSizeElem());
        int temp = (int)Math.floor(Math.random() *(nBitsTotal-1));
        temp+=1; //selecciona un bit por el que cortar
        for(int j = 0; j < temp; ++j){
            boolean helper = cods[codIdxA].bitset.get(j);
            cods[codIdxA].bitset.set(j,cods[codIdxB].bitset.get(j));
            cods[codIdxB].bitset.set(j,helper);
        }
    }

    public void crossAll(codificacion_real[] cods, int codIdxA, int codIdxB){
        int nBitsTotal =  cods[codIdxA].floatVec.length;
        int temp = (int)Math.floor(Math.random() *(nBitsTotal-1));
        temp+=1; //selecciona un bit por el que cortar
        for(int i = 0; i < temp; ++i){
            float helper = cods[codIdxA].floatVec[i];
            cods[codIdxA].floatVec[i] = cods[codIdxB].floatVec[i];
            cods[codIdxB].floatVec[i] = helper;
        }
    }

}
