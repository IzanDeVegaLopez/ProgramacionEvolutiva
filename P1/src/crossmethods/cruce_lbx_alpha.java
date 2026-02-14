package crossmethods;

import codification.codificacion_real;

public class cruce_lbx_alpha {
    float ponderacion_cruce = 0.25f;
    float alpha = 0.5f;
    public void crossAll(codificacion_real[] cod, int idxCodA, int idxCodB){
        for(int i = 0; i < cod[idxCodA].getNElems()*cod[idxCodA].getSizeGenoma(); ++i){
            float auxA = cod[idxCodA].floatVec[i]; float auxB = cod[idxCodB].floatVec[i];
            float I = Math.abs(auxA-auxB);
            float minInInterval = Math.max(0,-alpha*I+Math.min(auxA,auxB));
            float maxInInterval = Math.min(Math.min(auxA,auxB)+alpha*I,cod[idxCodA].maxValues[i%cod[idxCodA].getSizeGenoma()]);
            float delta = maxInInterval - minInInterval;
            cod[idxCodA].floatVec[i] = (float) (Math.random() * delta + minInInterval);
            cod[idxCodB].floatVec[i] = (float) (Math.random() * delta + minInInterval);
        }
    }
}
