package codification;
import java.util.BitSet;
import mutation_methods.*;

public class codificacion_binaria {
    public BitSet bitset;
    int sizeElem;
    int nElems;
    public int[] sizeParts;
    public codificacion_binaria(int nFilas, int nColumnas, int nCamaras) {
        nElems = nCamaras;
        int sizeFilas = 1+(int) (Math.log(nFilas) / Math.log(2));
        int sizeColumnas = 1+(int) (Math.log(nColumnas) / Math.log(2));
        sizeElem = sizeFilas+sizeColumnas;
        bitset = new BitSet(nCamaras*(sizeElem));
        sizeParts = new int[]{sizeFilas, sizeColumnas};
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

    public static codificacion_binaria getTestCod(){
        codificacion_binaria cod = new codificacion_binaria(12,15,2);
        cod.bitset.set(0,true);
        cod.bitset.set(1,true);
        cod.bitset.set(4, true);
        cod.bitset.set(5,true);
        cod.bitset.set(8,true);
        cod.bitset.set(12, true);
        return cod;
    }
}
