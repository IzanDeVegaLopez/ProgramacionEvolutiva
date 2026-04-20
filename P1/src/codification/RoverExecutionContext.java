package codification;

import Mapas.Map;
import Mapas.TileContents;
import utils.Vector2;
import Error.UnreachableCode;

import java.util.Vector;

public class RoverExecutionContext {
    static final int total_energy = 150;
    static final int total_ticks = 150;
    public enum rotationDirection{
        RD_LEFT,
        RD_RIGHT,
        DEFAULT
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

    int turn_count = 0;
    rotationDirection last_rotation = rotationDirection.DEFAULT;

    Vector<Vector2> tiles = new Vector<>();

    boolean add_reward = false;

    int sample_count = 0;
    int tile_count = 0;
    int reward_shaping = 0;
    int sand_count = 0;
    int crash_count = 0;

    public void rotate(rotationDirection rotDir) throws Exception{
        if(rotDir== rotationDirection.RD_RIGHT){
            lookingAtIdx = lookingAtIdx-1;
            if(lookingAtIdx < 0) lookingAtIdx += 3;
        }else if(rotDir==rotationDirection.RD_LEFT){
            lookingAtIdx = (lookingAtIdx+1) %4;
        }
        --energy_remaining;

        if (last_rotation == rotDir){
            if (++turn_count > 3){
                energy_remaining -=20;
                turn_count = 0;
            }
        }
        else turn_count = 0;
        last_rotation = rotDir;


//        throw new UnreachableCode("Agregar penalización por mareo, restar energía");
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
            looking_at_tile = looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
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
            looking_at_tile = looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
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
            looking_at_tile = looking_at_tile.add(DIRECTIONS[lookingAtIdx]);
            ++dist;
        }
        if(current_map.get_tile(looking_at_tile) == TileContents.SAMPLE) add_reward = true;
        return dist;
        //return 0;
    }
    public int get_energy_level() throws Exception{
        return energy_remaining;
    }

    public void advance() throws Exception{
        Vector2 newTile = currentTile.clone();
        newTile = newTile.add(DIRECTIONS[lookingAtIdx]);
        switch (current_map.get_tile(newTile)){
            case SAMPLE:
                ++sample_count;
            case EMPTY:
                currentTile = newTile.clone();
                --energy_remaining;
                if (!current_map.tainted[currentTile.y][currentTile.x])
                    ++tile_count;
                break;
            case SAND:
                energy_remaining-=10;
                currentTile = newTile.clone();
                ++sand_count;
                if (!current_map.tainted[currentTile.y][currentTile.x])
                    ++tile_count;
                break;
            case WALL:
                energy_remaining-=2;
                ++crash_count;
//                System.out.println("owie");
                break;
        }
        if (add_reward) reward_shaping++;
        current_map.tainted[currentTile.y][currentTile.x] = true;
        add_reward = false;
    }
    public void advance(with_tiles t) throws Exception{
        Vector2 newTile = currentTile.clone();
        newTile = newTile.add(DIRECTIONS[lookingAtIdx]);
        switch (current_map.get_tile(newTile)) {
            case SAMPLE:
                ++sample_count;
            case EMPTY:
                currentTile = newTile.clone();
                --energy_remaining;
                if (!current_map.tainted[currentTile.y][currentTile.x]) {
                    tiles.add(newTile.clone());
                    ++tile_count;
                }
                break;
            case SAND:
                energy_remaining -= 10;
                currentTile = newTile.clone();
                ++sand_count;
                if (!current_map.tainted[currentTile.y][currentTile.x]) {
                    tiles.add(newTile.clone());
                    ++tile_count;
                }
                break;
            case WALL:
                energy_remaining -= 2;
                ++crash_count;
//                System.out.println("owie");
                break;
        }
        if (add_reward) reward_shaping++;
        current_map.tainted[currentTile.y][currentTile.x] = true;
        add_reward = false;
//        throw new UnreachableCode("Implementar choque contra muros, restar energia, coger samples");
    }

    public static class RecorridoReturnType{
        public int muestras_recogidas = 0;
        public int casillas_exploradas = 0;
        public int recompensa_visual = 0;
        public int arena = 0;
        public int colisiones = 0;

        public RecorridoReturnType(int sampleCount, int tileCount, int rewardShaping, int sandCount, int crashCount) {
            muestras_recogidas = sampleCount;
            casillas_exploradas = tileCount;
            recompensa_visual = rewardShaping;
            arena = sandCount;
            colisiones = crashCount;
        }
    }
    public static enum with_tiles{
        WITH_TILES
    }
    public static class RecorridoReturnTypeWithTilesReached{
        public RecorridoReturnType rrt;
        public Vector<Vector2> all_tiles_reached;
        public RecorridoReturnTypeWithTilesReached(RecorridoReturnType r, Vector<Vector2> t){
            rrt = r;
            all_tiles_reached = t;
        }
    }
    public void reset(){
        currentTile = new Vector2(1,1);
        lookingAtIdx = 0;
        energy_remaining = total_energy;
    }
    public RecorridoReturnType do_simulation(Map m, IndividualCodification cod) throws Exception{
        reset();
        int ticks = 0;
        current_map = m;
        current_map.resetTainted();
        while(ticks < total_ticks && energy_remaining > 0) {
            cod.execute(this);
            ++ticks;
        }
        return new RecorridoReturnType(sample_count,tile_count,reward_shaping,sand_count,crash_count);
    }
    public RecorridoReturnTypeWithTilesReached do_simulation(Map m, IndividualCodification cod, with_tiles t) throws Exception{
        reset();
        int ticks = 0;
        current_map = m;
        while(ticks < total_ticks && energy_remaining > 0){
            cod.execute(this, t);
            ++ticks;
        }
        return new RecorridoReturnTypeWithTilesReached(
                new RecorridoReturnType(sample_count,tile_count,reward_shaping,sand_count,crash_count),
                tiles);
    }
}
