package crossmethods;

import codification.codificacion_entera;

public class ox_cross  implements base_cross_method{
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        //Escoger dos puntos de corte al azar;
        int corte1 = (int) Math.floor(padre1.get_size() * Math.random());
        int corte2 = (int) Math.floor(padre2.get_size() * Math.random());
        //Ensure corte 1 <= corte2
        if (corte2 < corte1) {
            //SWAP corte1 : corte2
            corte1 += corte2;
            corte2 = corte1 - corte2;
            corte1 -= corte2;
        }

        //Crear los 2 arrays a devolver
        codificacion_entera child1 = new codificacion_entera(padre1.get_size());
        codificacion_entera child2 = new codificacion_entera(padre2.get_size());

        //Copy elements directamente dentro de los cortes
        for (int i = corte1; i < corte2; ++i) {
            child1.set_value(i, padre1.get_value(i));
            child2.set_value(i, padre2.get_value(i));
        }

        //Coger todos los elementos del otro padre que no estuviesen ya en ese hijo en ese orden
        int[] lista_elementos_child1 = new int[child1.get_free()];
        int[] lista_elementos_child2 = new int[child2.get_free()];
        int index_child1=0,index_child2=0;
        for (int i = 0; i < child1.get_size(); ++i) {
            int value_padre2 = padre2.get_value(i);
            if(child1.get_conflict_index(value_padre2)==-1){
                lista_elementos_child1[index_child1++] = value_padre2;
            }

            int value_padre1 = padre1.get_value(i);
            if(child2.get_conflict_index(value_padre1)==-1){
                lista_elementos_child2[index_child2++] = value_padre1;
            }
        }
        index_child1=0;index_child2=0;
        //Antes del corte
        for(int i = 0; i < corte1; ++i){
            child1.set_value(i, lista_elementos_child1[index_child1++]);
            child2.set_value(i, lista_elementos_child2[index_child2++]);
        }
        // después del corte
        for(int i = corte2; i < child1.get_size(); ++i){
            child1.set_value(i, lista_elementos_child1[index_child1++]);
            child2.set_value(i, lista_elementos_child2[index_child2++]);
        }

        padre1.copy(child1);
        padre2.copy(child2);
    }
}
