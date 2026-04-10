package Mapas;

import utils.Vector2;

public class mapGenerator {
    public static Map generateMap(int x, int y, long seed, int walls, int sand_tiles, int samples){
        int[][] importance_map = new int[y][x];
        TileContents[][] ocup_map = new TileContents[y][x];

        for (int i = 0; i<y;i++){
            for (int j = 0; j<x;j++){
                boolean wall = i == 0 || i == y-1 || j == 0 || j == x-1;
                importance_map[i][j] = wall ? 0 : 1;
                ocup_map[i][j] = wall ? TileContents.WALL : TileContents.EMPTY;
            }
        }
        Map new_map = new Map(importance_map,ocup_map);

        int final_sand_tiles = walls+sand_tiles;
        int final_tiles = final_sand_tiles+samples;
        Vector2[] wall_positions =  new_map.getRandomTiles(final_tiles, seed);
        int i = 0;
        for (Vector2 v : wall_positions){
            if (i < walls){
                new_map.set_contents(v.x,v.y,TileContents.WALL);
            }
            else if (i<final_sand_tiles){
                new_map.set_contents(v.x,v.y,TileContents.SAND);
            }
            else{
                new_map.set_contents(v.x,v.y,TileContents.SAMPLE);
            }
            ++i;
        }
        return new_map;
    }
}
