package codification;
import java.util.BitSet;
import java.util.List;

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

    public void setAllData(BitSet b) {
        if (b.size() != bitset.size()) {
            System.out.println("El tamaño del elemento no coindice en llamada a codificacion_binaria::set_elem");
            return;
        }
        bitset = (BitSet)b.clone();
    }
    public BitSet retrieveAllData(){
        return bitset;
    }
    public void swap(int elemIdxA, int elemIdxB, int elem){
        boolean aux = bitset.get(elemIdxA * getSizeElem()+elem);
        bitset.set(elemIdxA*getSizeElem()+elem, bitset.get(elemIdxB*getSizeElem()+elem));
        bitset.set(elemIdxB*getSizeElem()+elem, aux);
    }
    public boolean[] get_elem(int elemIdx){
        boolean[] aux = new boolean[sizeElem];
        for(int i = 0; i < sizeElem; ++i){
            aux[i] = bitset.get(elemIdx*getSizeElem()+i);
        }
        return aux;
    }
    public void set_elem(boolean[] b, int elemIdx){
        if(b.length != getSizeElem()){
            System.out.println("El tamaño del elemento no coindice en llamada a codificacion_binaria::set_elem");
            return;
        }
        for(int i = 0; i < sizeElem; ++i){
            bitset.set(elemIdx*getSizeElem()+i, b[i]);
        }
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

    public int[] getElemI(int idx){
        int[] retVal = new int[sizeParts.length];
        //Contruye números a partir de binarios
        //Presupone que el bit menos significativo está a la izquierda
        int sizeAcum = 0;
        for(int i = 0; i < sizeParts.length; ++i){
            int x = 0;
            for(int ix = 0; ix < sizeParts[i]; ++ix){
                x += (bitset.get(idx*sizeElem+ix+sizeAcum) ? 1 : 0) << ix;
            }
            sizeAcum+=sizeParts[i];
            retVal[i] = x;
        }
        return retVal;
    }
}
