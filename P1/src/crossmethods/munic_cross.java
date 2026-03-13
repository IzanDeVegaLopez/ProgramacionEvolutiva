package crossmethods;

import codification.codificacion_entera;

public class munic_cross implements base_cross_method{
    public void cruzar(codificacion_entera element1, codificacion_entera element2) {
        int size = element1.get_size();
        codificacion_entera victor = new codificacion_entera(size);
        codificacion_entera mictor = new codificacion_entera(size);

        for (int i = 0; i<element1.get_size();++i){
            int id1 = element1.get_value(i);
            int id2 = element2.get_value(i);
            if (victor.is_contained(id1) || mictor.is_contained(id2)){
                victor.set_value(i,id2);
                mictor.set_value(i,id1);
            }
            else if (victor.is_contained(id2) || mictor.is_contained(id1)){
                victor.set_value(i,id1);
                mictor.set_value(i,id2);
            }
            else{
                int atk_idx = (i - 1) % size;
                int arm_idx = (i + 1) % size;
                gladiator glad1 = new gladiator(element1.get_value(i),
                        element1.get_value(atk_idx),
                        element1.get_value(arm_idx));
                gladiator glad2 = new gladiator(element2.get_value(i),
                        element2.get_value(atk_idx),
                        element2.get_value(arm_idx));
                boolean first_attacking = glad1.spd >= glad2.spd;
                while (glad1.arm > 0 && glad2.arm > 0) {
                    if (first_attacking) {
                        glad2.arm -= glad1.atk;
                    } else {
                        glad1.arm -= glad2.atk;
                    }
                    first_attacking = !first_attacking;
                }
                victor.set_value(i,glad2.arm<0? id1 : id2 );
                mictor.set_value(i,glad2.arm<0? id2 : id1 );
            }
        }
        element1.copy(victor);
        element2.copy(mictor);
    }
}
