package codification;

import Mapas.Map;
import Mapas.TileContents;
import utils.Vector2;
import Error.UnreachableCode;

import java.util.Vector;

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
    Map current_map;
    int energy_remaining = total_energy;
    public void rotate(rotationDirection rotDir) throws Exception{
        if(rotDir== rotationDirection.RD_RIGHT){
            lookingAtIdx = lookingAtIdx-1;
            if(lookingAtIdx < 0) lookingAtIdx += 3;
        }else if(rotDir==rotationDirection.RD_LEFT){
            lookingAtIdx = lookingAtIdx+1 %4;
        }

        throw new UnreachableCode("Agregar penalización por mareo, restar energía");
    }
    public void rotate(rotationDirection rotDir, with_tiles t) throws Exception{
        rotate(rotDir);
    }

    public int get_sand_dist() throws Exception {
        int dist = 1;
        Vector2 looking_at_tile = currentTile.add(DIRECTIONS[lookingAtIdx]);
        while(current_map.validTile(looking_at_tile)){
            TileContents tile = current_map.get_tile(looking_at_tile);
            if(tile == TileContents.SAND) break;
            looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
            ++dist;
        }
        return dist;
    }
    public int get_obstacle_dist() throws Exception {
        int dist = 1;
        Vector2 looking_at_tile = currentTile.add(DIRECTIONS[lookingAtIdx]);
        while(current_map.tileWithinBounds(looking_at_tile)){
            TileContents tile = current_map.get_tile(looking_at_tile);
            if(tile == TileContents.WALL) break;
            looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
            ++dist;
        }
        return dist;
        //return 0;
    }
    public int get_sample_dist() throws Exception {
        int dist = 1;
        Vector2 looking_at_tile = currentTile.add(DIRECTIONS[lookingAtIdx]);
        while(current_map.validTile(looking_at_tile)){
            TileContents tile = current_map.get_tile(looking_at_tile);
            if(tile == TileContents.SAMPLE) break;
            looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
            ++dist;
        }
        return dist;
        //return 0;
    }
    public int get_energy_level() throws Exception{
        return energy_remaining;
    }

    public void advance() throws Exception{
        currentTile.add(DIRECTIONS[lookingAtIdx]);
        throw new UnreachableCode("Implementar choque contra muros, restar energia, coger samples");
    }
    public void advance(with_tiles t) throws Exception{
        currentTile.add(DIRECTIONS[lookingAtIdx]);
        throw new UnreachableCode("Implementar choque contra muros, restar energia, coger samples");
    }

    public static class RecorridoReturnType{
        public int muestras_recogidas = 0;
        public int casillas_exploradas = 0;
        public int recompensa_visual = 0;
        public int arena = 0;
        public int colisiones = 0;
    }
    public static enum with_tiles{
        WITH_TILES
    }
    public static class RecorridoReturnTypeWithTilesReached{
        public RecorridoReturnType rrt;
        public Vector<Vector2> all_tiles_reached;
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
    public RecorridoReturnTypeWithTilesReached do_simulation(Map m, IndividualCodification cod, with_tiles t) throws Exception{
        reset();
        while(energy_remaining > 0){
            cod.execute(this, t);
        }
        throw new UnreachableCode("Falta devolver el valor, y hacer todas las comprobaciones de casillas en las respectivas funciones de moverse y girar");
    }
}
