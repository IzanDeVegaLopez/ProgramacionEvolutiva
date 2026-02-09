package codification;
import java.util.BitSet;

public class codificacion_binaria {
    public BitSet bitset;
    int sizeElem;
    int nElems;
    public codificacion_binaria(int nFilas, int nColumnas, int nCamaras) {
        nElems = nCamaras;
        int sizeFilas = (int) (Math.log(nFilas) / Math.log(2));
        int sizeColumnas = (int) (Math.log(nColumnas) / Math.log(2));
        sizeElem = sizeFilas+sizeColumnas;
        bitset = new BitSet(nCamaras*(sizeElem));
    }

    public int getNElems(){
        return nElems;
    }
    public int getSizeElem(){
        return sizeElem;
    }

    public void swap(int elemIdxA, int elemIdxB, int elem){
        boolean aux = bitset.get(elemIdxA * getSizeElem()+elem);
        bitset.set(elemIdxA*getSizeElem()+elem, bitset.get(elemIdxB*getSizeElem()+elem));
        bitset.set(elemIdxB*getSizeElem()+elem, aux);
    }
}
