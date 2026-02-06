package codification;
import java.util.BitSet;

public class codificacion_binaria {
    BitSet bitset;
    int sizeColumnas, sizeFilas;
    public codificacion_binaria(int nFilas, int nColumnas, int nCamaras) {
        sizeFilas = (int) (Math.log(nFilas) / Math.log(2));
        sizeColumnas = (int) (Math.log(nFilas) / Math.log(2));
        bitset = new BitSet(nCamaras*(sizeFilas+sizeColumnas));
    }
}
