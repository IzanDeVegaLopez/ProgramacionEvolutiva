package crossmethods;

import codification.codificacion_entera;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.BitSet;

public class erx_cross implements base_cross_method{
    private class tabla_de_conectividad{
        java.util.ArrayList<Integer>[] mapa;
        BitSet taken;
        int freacking_size;
        public tabla_de_conectividad my_clone(){
            tabla_de_conectividad tab = new tabla_de_conectividad(mapa.length);
            tab.mapa = mapa.clone();
            tab.taken = new BitSet(mapa.length);
            return tab;
        }
        public int[] get_ady(int i){
            ArrayList<Integer> tmp = new ArrayList<>();
            for(int x = 0; x < mapa[i].size(); ++x) {
                int vecino = mapa[i].get(x);
                if (!taken.get(vecino)) {
                    tmp.add(vecino);
                }
            }
            int[] ret = new int[tmp.size()];
            for(int x = 0; x < tmp.size(); ++x){
                ret[x] = tmp.get(x);
            }
            return ret;
        }
        public void set_as_taken(int i){
            taken.set(i);
        }
        public int get_non_visited_idx(){
            int random_idx = (int) Math.floor(Math.random() * mapa.length);
            int next_non_visited = taken.nextClearBit(random_idx);
            if(next_non_visited >= mapa.length){
                next_non_visited = taken.nextClearBit(0);
            }
            set_as_taken(next_non_visited);
            return next_non_visited;
        }
        public int take_vecino_con_menos_conexiones(int[] vecinos){
            int idx_vecino_con_menos_conexiones = vecinos[0];
            int min_vecinos = n_conexiones(vecinos[0]);

            for(int i = 1; i < vecinos.length; ++i){
                int new_n_conexiones = n_conexiones(vecinos[i]);
                if(new_n_conexiones < min_vecinos){
                    min_vecinos = new_n_conexiones;
                    idx_vecino_con_menos_conexiones = vecinos[i];
                }
            }

            set_as_taken(idx_vecino_con_menos_conexiones);

            return idx_vecino_con_menos_conexiones;
        }
        private int n_conexiones(int idx){
            int ret_value = 0;
            for(int i = 0; i < mapa[idx].size(); ++i){
                if(!taken.get(mapa[idx].get(i))){
                    ++ret_value;
                }
            }
            return ret_value;
        }
        public tabla_de_conectividad(int size){
            taken = new BitSet(size);
            taken.clear();
        }
        public tabla_de_conectividad(codificacion_entera cod1, codificacion_entera cod2){
            BitSet b = new BitSet(cod1.get_size());
            int last_elem = cod1.get_size()-1;

            mapa = new java.util.ArrayList[cod1.get_size()];
            for(int i = 0; i < cod1.get_size(); ++i){
                mapa[i] = new ArrayList<>();
            }

            if(cod1.get_size() > 1) {
                //FIRST
                //b.clear();
                add_if_unchecked(cod1.get_value(0), cod1.get_value(1));
                add_if_unchecked(cod1.get_value(0), cod1.get_value(last_elem));
                add_if_unchecked(cod2.get_value(0), cod2.get_value(1));
                add_if_unchecked(cod2.get_value(0), cod2.get_value(last_elem));

                //LAST
                //b.clear();
                add_if_unchecked(cod1.get_value(last_elem),cod1.get_value(0));
                add_if_unchecked(cod1.get_value(last_elem),cod1.get_value(last_elem-1));
                add_if_unchecked(cod2.get_value(last_elem),cod2.get_value(0));
                add_if_unchecked(cod2.get_value(last_elem),cod2.get_value(last_elem-1));
            }

            //IN BETWEEN
            b.clear();
            for(int i = 1; i < last_elem; ++i){
                add_if_unchecked(cod1.get_value(i),cod1.get_value(i+1));
                add_if_unchecked(cod1.get_value(i),cod1.get_value(i-1));
                add_if_unchecked(cod2.get_value(i), cod2.get_value(i+1));
                add_if_unchecked(cod2.get_value(i),cod2.get_value(i-1));
            }

            taken = new BitSet(cod1.get_size());
            taken.clear();
        }
        private void add_if_unchecked(int idx, int i){
            //if(!b.get(i)){
            //    b.set(i);
               mapa[idx].add(i);
            //}
        }
        public void take(int i){
            taken.set(i);
        }
    }
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        //Crear los 2 arrays a devolver
        //codificacion_entera child2 = new codificacion_entera(padre2.get_size());

        tabla_de_conectividad tab1 = new tabla_de_conectividad(padre1,padre2);
        tabla_de_conectividad tab2 = tab1.my_clone();
        
        padre1.copy(construir_hijo(padre1, tab1));
        padre2.copy(construir_hijo(padre2, tab2));
    }

    public codificacion_entera construir_hijo(codificacion_entera padre1, tabla_de_conectividad tab){
        codificacion_entera child1 = new codificacion_entera(padre1.get_size());
        if(padre1.get_size() > 0) {
            int cam_actual = padre1.get_value(0);
            tab.set_as_taken(cam_actual);
            child1.set_value(0, cam_actual);

            int pos = 1;
            while (child1.get_free() > 0) {
                int[] vecinos = tab.get_ady(cam_actual);
                if (vecinos.length > 0) {
                    cam_actual = tab.take_vecino_con_menos_conexiones(vecinos);
                } else {
                    cam_actual = tab.get_non_visited_idx();
                }
                child1.set_value(pos, cam_actual);
                ++pos;
            }
        }
        return child1;
    }
}
