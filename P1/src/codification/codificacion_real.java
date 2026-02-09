package codification;

public class codificacion_real {
    public float[] floatVec;
    int sizeGenoma = 2;
    int nElems;
    public codificacion_real(int n) {
        nElems = n;
        floatVec = new float[nElems*sizeGenoma];
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
}
