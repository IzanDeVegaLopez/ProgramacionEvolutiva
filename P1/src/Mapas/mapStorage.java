package Mapas;


public class mapStorage {
    // Map templates that should not be changed during runtime
    private static Map[] map_blueprints = new Map[3];
    // Maps that may be changed during runtime
    private static Map[] maps = new Map[3];
    public static void add_map(Map m, int n){
        map_blueprints[n] = m;
        reset_maps();
    }
    public static Map get_map(int n){
        if (n>maps.length) return new Map(new TileContents[0][0]);
        return maps[n];
    }
    public static void reset_maps(){
        System.arraycopy(map_blueprints, 0, maps, 0, 3);
    }
}
