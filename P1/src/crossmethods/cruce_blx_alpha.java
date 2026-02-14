package crossmethods;

import codification.codificacion_real;

public class cruce_blx_alpha {
    float ponderacion_cruce = 0.25f;
    float alpha = 0.5f;
    public void crossAll(codificacion_real[] cod, int idxCodA, int idxCodB){
        int end = cod[idxCodA].getNElems()*cod[idxCodA].getSizeGenoma();
        for(int i = 0; i < end; ++i){
            float auxA = cod[idxCodA].floatVec[i]; float auxB = cod[idxCodB].floatVec[i];
            float I = Math.abs(auxA-auxB);
            float minInInterval = Math.max(0,-alpha*I)+Math.min(auxA,auxB);
            float maxInInterval = Math.min(Math.max(auxA,auxB)+alpha*I,cod[idxCodA].maxValues[i%cod[idxCodA].getSizeGenoma()]);
            float delta = Math.abs(maxInInterval - minInInterval);
            cod[idxCodA].floatVec[i] = Math.min(Math.max(0,(float) (Math.random() * delta + minInInterval)), cod[idxCodA].maxValues[i%cod[idxCodA].getSizeGenoma()]-1);
            cod[idxCodB].floatVec[i] = Math.min(Math.max(0,(float) (Math.random() * delta + minInInterval)), cod[idxCodB].maxValues[i%cod[idxCodB].getSizeGenoma()]-1);
        }
    }
}
