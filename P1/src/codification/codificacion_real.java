package codification;
import mutation_methods.*;

public class codificacion_real {
    public float[] floatVec;
    // Almacena el valor máximo que puede generar para cada gen
    private float[] maxValues;
    int sizeGenoma = 2;
    int nElems;
    public codificacion_real(int n) {
        nElems = n;
        floatVec = new float[nElems*sizeGenoma];
        maxValues = new float[sizeGenoma];
    }
    public int getNElems(){
        return nElems;
    }
    public int getSizeGenoma() {
        return sizeGenoma;
    }

    public void swap(int elemIdxA, int elemIdxB, int elem){
        float aux = floatVec[elemIdxA * getSizeGenoma()+elem];
        floatVec[elemIdxA*getSizeGenoma()+elem] = floatVec[elemIdxB*getSizeGenoma()+elem];
        floatVec[elemIdxB*getSizeGenoma()+elem] = aux;
    }

    // Muta el gen `elem` del cromosoma `elemIdx`.
    public void mutate_at(int elemIdx, int elem){
        floatVec[elemIdx*getSizeGenoma()+elem] = (float)Math.random() * maxValues[elem];
    }

}
