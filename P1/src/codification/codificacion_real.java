package codification;
import mutation_methods.*;

public class codificacion_real {
    public float[] floatVec;
    // Almacena el valor máximo que puede generar para cada gen
    private float[] maxValues;
    int sizeGenoma = 3;
    int nElems;
    public codificacion_real(int nCams, int maxX, int maxY) {
        nElems = nCams;
        floatVec = new float[nElems*sizeGenoma];
        maxValues = new float[sizeGenoma];
        maxValues[0]=maxX;
        maxValues[1]=maxY;
        maxValues[2]=360;
    }
    public void setAllData(float[] val){
        floatVec = val.clone();
    }
    public float[] retrieveAllData(){
        return floatVec;
    }
    public int getNElems(){
        return nElems;
    }
    public int getSizeGenoma() {
        return sizeGenoma;
    }
    public float[] getElemI(int elem){
        float[] ret = new float[sizeGenoma];
        for(int i = 0; i < sizeGenoma; ++i){
            ret[i] = floatVec[sizeGenoma*elem+i];
        }
        return ret;
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
