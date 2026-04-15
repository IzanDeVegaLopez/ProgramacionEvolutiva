package codification;

import Mapas.Map;
import utils.Vector2;
import Error.UnreachableCode;

public class RoverExecutionContext {
    static final int total_energy = 150;
    public enum rotationDirection{
        RD_LEFT,
        RD_RIGHT
    }
    public static Vector2 DIRECTIONS [] =
    {
        new Vector2(1,0), // R
        new Vector2(0,1), // U
        new Vector2(-1,0),// L
        new Vector2(0,-1) // D
    };
    int lookingAtIdx = 0;
    Vector2 currentTile = new Vector2(1,1);
    int energy_remaining = total_energy;
    public void rotate(rotationDirection rotDir){
        if(rotDir== rotationDirection.RD_RIGHT){
            lookingAtIdx = lookingAtIdx-1;
            if(lookingAtIdx < 0) lookingAtIdx += 3;
        }else if(rotDir==rotationDirection.RD_LEFT){
            lookingAtIdx = lookingAtIdx+1 %4;
        }
    }

    public int get_sand_dist() throws Exception {
        throw new UnreachableCode("Unimplemented");
        //return 0;
    }
    public int get_obstacle_dist() throws Exception {
        throw new UnreachableCode("Unimplemented");
        //return 0;
    }
    public int get_sample_dist() throws Exception {
        throw new UnreachableCode("Unimplemented");
        //return 0;
    }
    public int get_energy_level() throws Exception{
        throw new UnreachableCode("Unimplemented");
    }

    public void advance(){
        currentTile.add(DIRECTIONS[lookingAtIdx]);
    }

    public static class RecorridoReturnType{
        public int muestras_recogidas = 0;
        public int casillas_exploradas = 0;
        public int recompensa_visual = 0;
        public int arena = 0;
        public int colisiones = 0;
    }
    public void reset(){
        currentTile = new Vector2(1,1);
        lookingAtIdx = 0;
        energy_remaining = total_energy;
    }
    public RecorridoReturnType do_simulation(Map m, IndividualCodification cod) throws Exception{
        reset();
        while(energy_remaining > 0) {
            cod.execute(this);
        }
        throw new UnreachableCode("Falta devolver el valor, y hacer todas las comprobaciones de casillas en las respectivas funciones de moverse y girar");
    }
}
