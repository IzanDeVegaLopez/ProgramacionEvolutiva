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

    public codificacion_binaria() {
    }
    
    /// Cruce uniforme. `ratio` es un número entre 0 y 1, representando la probabilidad de que un gen del cromosoma original se reemplaze por el gen correspondiente del otro.
    public codificacion_binaria cruce_uniforme(codificacion_binaria other, double ratio){
        BitSet newbitset = new BitSet(bitset.length());
        for (int i = 0; i<bitset.length();i++) {
            double temp = Math.random();
            newbitset.set(i, temp >= ratio ? bitset.get(i) : other.bitset.get(i));
        }
        return new codificacion_binaria();
    }

    /// Cruce monopunto. `point` define el punto tras el cual los genes se reemplazan por los correspondientes del otro.
    public codificacion_binaria cruce_monopunto(codificacion_binaria other, int point){
        BitSet newbitset = new BitSet(bitset.length());
        for (int i = 0; i<bitset.length();i++) {
            newbitset.set(i, i < point ? bitset.get(i) : other.bitset.get(i));
        }
        return new codificacion_binaria();
    }
}
