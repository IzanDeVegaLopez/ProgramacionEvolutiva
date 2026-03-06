package crossmethods;

import codification.codificacion_entera;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.BitSet;

public class erx_cross implements base_cross_method{
    private class tabla_de_conectividad{
        java.util.ArrayList<Integer>[] mapa;
        BitSet taken;
        public tabla_de_conectividad my_clone(){
            tabla_de_conectividad tab = new tabla_de_conectividad(taken.size());
            tab.mapa = mapa.clone();
            taken = new BitSet(taken.size());
        }
        public int[] get_ady(int i){
            //int[] ret = Arrays.stream(mapa[i].toArray()).mapToInt(Integer::intValue).ToArray();
            ArrayList<Integer> array_list_intermedia = new ArrayList<>();
            for(int x = 0; x < mapa[i].size(); ++x) {
                if (!taken.get(mapa[i].get(x))) {
                    array_list_intermedia.add(mapa[i].get(i));
                    //taken.set(mapa[i].get(x));
                }
            }
            int[] array_to_return = new int[array_list_intermedia.size()];
            for(int x = 0; x < array_list_intermedia.size(); ++x){
                array_to_return[i] = array_list_intermedia.get(x);
            }
            return array_to_return;
        }
        public void set_as_taken(int i){
            taken.set(i);
        }
        public int get_non_visited_idx(){
            int random_idx = (int) (Math.random() * taken.size());
            int next_non_visited = taken.nextClearBit(random_idx);
            if(next_non_visited >= taken.size()){
                next_non_visited = taken.nextClearBit(0);
            }
            set_as_taken(next_non_visited);
            return next_non_visited;
        }
        public int take_vecino_con_menos_conexiones(int[] vecinos){
            int idx_vecino_con_menos_conexiones = 0;
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

            //mapa = new ArrayList<Integer>[cod1.get_size()];
            //for(int i = 0; i < cod1.get_size(); ++i){
              //  mapa[i] = new ArrayList<>();
            //}
        }
        public tabla_de_conectividad(codificacion_entera cod1, codificacion_entera cod2){
            BitSet b = new BitSet(cod1.get_size());
            int last_elem = cod1.get_size()-1;

            mapa = new ArrayList<Integer>[cod1.get_size()];
            for(int i = 0; i < cod1.get_size(); ++i){
                mapa[i] = new ArrayList<>();
            }

            //FIRST
            b.clear();
            add_if_unchecked(0,cod1.get_value(1),b);
            add_if_unchecked(0,cod1.get_value(last_elem),b);
            add_if_unchecked(0,cod2.get_value(1),b);
            add_if_unchecked(0,cod2.get_value(last_elem),b);

            //IN BETWEEN
            b.clear();
            for(int i = 1; i < last_elem-1; ++i){
                add_if_unchecked(i,cod1.get_value(i+1),b);
                add_if_unchecked(i,cod1.get_value(i-1),b);
                add_if_unchecked(i,cod2.get_value(i+1),b);
                add_if_unchecked(i,cod2.get_value(i-1),b);
            }

            //LAST
            b.clear();
            add_if_unchecked(last_elem,cod1.get_value(0),b);
            add_if_unchecked(last_elem,cod1.get_value(last_elem-1),b);
            add_if_unchecked(last_elem,cod2.get_value(0),b);
            add_if_unchecked(last_elem,cod2.get_value(last_elem-1),b);

            taken = new BitSet(cod1.get_size());
            taken.clear();
        }
        private void add_if_unchecked(int idx, int i, BitSet b){
            if(!b.get(i)){
                b.set(i);
                mapa[idx].add(i);
            }
        }
        public void take(int i){
            taken.set(i);
        }
    }
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        //Crear los 2 arrays a devolver
        codificacion_entera child2 = new codificacion_entera(padre2.get_size());

        tabla_de_conectividad tab1 = new tabla_de_conectividad(padre1,padre2);
        tabla_de_conectividad tab2 = tab1.my_clone();
        
        padre1.copy(construir_hijo(padre1, tab1));
        padre2.copy(construir_hijo(padre2, tab2));
    }

    public codificacion_entera construir_hijo(codificacion_entera padre1, tabla_de_conectividad tab){
        codificacion_entera child1 = new codificacion_entera(padre1.get_size());
        int cam_actual = padre1.get_value(0);
        tab.set_as_taken(0);
        child1.set_value(0,cam_actual);

        int i = 0;
        while(child1.get_free() > 0){
            int[] vecinos = tab.get_ady(cam_actual);
            if(vecinos.length > 0){
                cam_actual = tab.take_vecino_con_menos_conexiones(vecinos);
            }else{
                cam_actual = tab.get_non_visited_idx();
            }
            child1.set_value(i++,cam_actual);
        }
        return child1;
    }
}
