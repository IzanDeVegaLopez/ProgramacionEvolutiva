package crossmethods;

import codification.codificacion_entera;

public class munic_cross implements base_cross_method{
    int armor_factor = 2;
    public void cruzar(codificacion_entera element1, codificacion_entera element2) {
        int size = element1.get_size();
        codificacion_entera victor = new codificacion_entera(size);
        codificacion_entera mictor = new codificacion_entera(size);

        int idx1 = 0;
        int idx2 = 0;
        for (int i = 0; i<element1.get_size();++i){
            int val1 = element1.get_value(i);
            int val2 = element2.get_value(i);

            boolean[] checks = new boolean[]{victor.is_contained(val1), mictor.is_contained(val1),
                    victor.is_contained(val2), mictor.is_contained(val2)};
            if (checks[0] || checks[1] || checks[2] || checks[3]) {
                if (checks[0]) {
                    mictor.set_value(idx2, val1);
                    ++idx2;
                } else {
                    victor.set_value(idx1, val1);
                    ++idx1;
                }

                if (checks[2]) {
                    mictor.set_value(idx2, val2);
                    ++idx2;
                } else {
                    victor.set_value(idx1, val2);
                    ++idx1;
                }
            }
            else{
                int atk_idx = i - 1;
                atk_idx = atk_idx < 0 ? size - 1 : atk_idx;
                int arm_idx = (i + 1) % size;
                gladiator glad1 = new gladiator(element1.get_value(i),
                        element1.get_value(atk_idx)+1,
                        element1.get_value(arm_idx)*armor_factor);
                gladiator glad2 = new gladiator(element2.get_value(i),
                        element2.get_value(atk_idx)+1,
                        element2.get_value(arm_idx)*armor_factor);
                boolean first_attacking = glad1.spd >= glad2.spd;
                while (glad1.arm > 0 && glad2.arm > 0) {
                    if (first_attacking) {
                        glad2.arm -= glad1.atk;
                    } else {
                        glad1.arm -= glad2.atk;
                    }
                    first_attacking = !first_attacking;
                }
                victor.set_value(idx1,glad2.arm<0? val1 : val2 );
                mictor.set_value(idx2,glad2.arm<0? val2 : val1 );
                ++idx1;
                ++idx2;
            }
        }
        element1.copy(victor);
        element2.copy(mictor);
    }
}
