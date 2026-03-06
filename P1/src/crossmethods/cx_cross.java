package crossmethods;

import codification.codificacion_entera;

public class cx_cross  implements base_cross_method{
    public void cruzar(codificacion_entera padre1, codificacion_entera padre2) {
        //Crear los 2 arrays a devolver
        codificacion_entera child1 = new codificacion_entera(padre1.get_size());
        codificacion_entera child2 = new codificacion_entera(padre2.get_size());

        int indice_actual = 0;
        //Encontrar el ciclo y asignar a los hijos
        while (child1.get_value(indice_actual)==-1) {
            child1.set_value(indice_actual, padre1.get_value(indice_actual));
            child2.set_value(indice_actual, padre2.get_value(indice_actual));

            indice_actual =
                    padre1.get_conflict_index(
                            padre2.get_value(indice_actual)
                    );
        }

        //Rellena el resto intercambiando padres
        for(int i = 0; i < padre1.get_size(); ++i){
            if(child1.get_value(i)==-1){
                child1.set_value(i,padre2.get_value(i));
                child2.set_value(i,padre1.get_value(i));
            }
        }

        padre1.copy(child1);
        padre2.copy(child2);
    }
}
