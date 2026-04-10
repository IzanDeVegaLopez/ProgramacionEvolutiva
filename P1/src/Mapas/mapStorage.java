package Mapas;


public class mapStorage {
    private static Map[] maps = new Map[3];
    public static void add_map(Map m, int n){
        maps[n] = m;
    }
    public static Map get_map(int n){
        if (n>maps.length) return new Map(new int[0][0], new TileContents[0][0]);
        return maps[n];
    }
}
