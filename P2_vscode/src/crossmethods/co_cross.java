package crossmethods;

import codification.codificacion_entera;

import java.util.BitSet;

public class co_cross implements base_cross_method{
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        int punto_corte = (int) (Math.random() * padre1.get_size());

        int[] ordinal_padre1 = convertir_a_ordinal(padre1);
        int[] ordinal_padre2 = convertir_a_ordinal(padre2);

        int[] ordinal_hijo1 = new int[padre1.get_size()];
        int[] ordinal_hijo2 = new int[padre2.get_size()];

        for(int i = 0; i < punto_corte; ++i){
            ordinal_hijo1[i] = ordinal_padre1[i];
            ordinal_hijo2[i] = ordinal_padre2[i];
        }
        for(int i = punto_corte; i < ordinal_hijo1.length; ++i){
            ordinal_hijo1[i] = ordinal_padre2[i];
            ordinal_hijo2[i] = ordinal_padre1[i];
        }

        padre1.copy(convertir_a_codificacion(ordinal_hijo1));
        padre2.copy(convertir_a_codificacion(ordinal_hijo2));
    }

    public int[] convertir_a_ordinal(codificacion_entera cod){
        BitSet b = new BitSet(cod.get_size());
        b.clear();

        int[] result = new int[cod.get_size()];
        for(int i = 0; i < cod.get_size(); ++i){
            int idx = 0;
            int penalizacion = 0;
            int value_to_reach = cod.get_value(i);
            //searches the next element
            while((idx = b.nextSetBit(idx)) < value_to_reach && idx != -1) {
                ++idx; ++penalizacion;
            }
            b.set(value_to_reach);
            result[i] = value_to_reach - penalizacion;
        }
        return result;
    }
    public codificacion_entera convertir_a_codificacion(int[] ordinal){
        BitSet b = new BitSet(ordinal.length);
        b.clear();

        codificacion_entera cod = new codificacion_entera(ordinal.length);
        for(int i = 0; i < ordinal.length; ++i){
            int idx = 0;
            int suma = 0;
            int value_to_reach = ordinal[i];

            //si esta posición está cogida salto a la siguiente, desciendo el contador solo cuando no lo esté
            while(value_to_reach>=0 && idx < ordinal.length){
                if(!b.get(idx)) --value_to_reach;
                else ++suma;
                ++idx;
            }
            int total_value =ordinal[i]+suma;
            b.set(total_value);
            cod.set_value(i, total_value);
        }
        return cod;
    }
}
