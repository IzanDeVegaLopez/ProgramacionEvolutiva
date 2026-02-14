package crossmethods;

import codification.codificacion_real;

public class cruce_aritmetico{
    float ponderacion_cruce = 0.25f;
    public void crossAll(codificacion_real[] cod, int idxCodA, int idxCodB){
        for(int i = 0; i < cod[idxCodA].getNElems()*cod[idxCodA].getSizeGenoma(); ++i){
                float auxA = cod[idxCodA].floatVec[i];
                cod[idxCodA].floatVec[i] = cod[idxCodB].floatVec[i]*ponderacion_cruce - auxA*(1-ponderacion_cruce);
                cod[idxCodB].floatVec[i] = auxA*ponderacion_cruce - cod[idxCodB].floatVec[i]*(1-ponderacion_cruce);
        }
    }
}
