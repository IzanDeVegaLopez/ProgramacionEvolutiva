package crossmethods;

import codification.codificacion_entera;

public class pmx_cross  implements base_cross_method{
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        //Escoger dos puntos de corte al azar;
        int corte1 = (int) Math.floor(padre1.get_size() * Math.random());
        int corte2 = (int) Math.floor(padre2.get_size() * Math.random());
        //Ensure corte 1 <= corte2
        if(corte2 < corte1){
            //SWAP corte1 : corte2
            corte1 += corte2;
            corte2 = corte1 - corte2;
            corte1 -= corte2;
        }

        //Crear los 2 arrays a devolver
        codificacion_entera child1 = new codificacion_entera(padre1.get_size());
        codificacion_entera child2 = new codificacion_entera(padre2.get_size());

        for(int i = corte1; i < corte2; ++i){
            int candidato1 = padre2.get_value(i);
            while(child1.is_contained(candidato1)){
                int indice_conflicto = padre2.get_conflict_index(candidato1);
                candidato1 = padre1.get_value(indice_conflicto);
            }
            child1.set_value(i,candidato1);

            int candidato2 = padre1.get_value(i);
            while(child2.is_contained(candidato2)){
                int indice_conflicto = padre1.get_conflict_index(candidato2);
                candidato2 = padre2.get_value(indice_conflicto);
            }
            child2.set_value(i,candidato2);
        }


        padre1 = child1;
        padre2 = child2;
    }
}
