package Mapas;

import utils.Vector2;

import java.util.Random;

public class mapGenerator {
    public static Map generateMap(int x, int y, long seed){
        int[][] importance_map = new int[y][x];
        TileContents[][] ocup_map = new TileContents[y][x];

        Random rand = new Random(seed);
        for (int i = 0; i<y;i++){
            for (int j = 0; j<x;j++){
                if (i == 0 || i == y-1 || j == 0 || j == x-1) ocup_map[i][j] = TileContents.WALL;
                else if (rand.nextDouble() < 0.15) ocup_map[i][j] = TileContents.WALL;
                else if (rand.nextDouble() < 0.15) ocup_map[i][j] = TileContents.SAND;
                else if (rand.nextDouble() < 0.08) ocup_map[i][j] = TileContents.SAMPLE;
                else ocup_map[i][j] = TileContents.EMPTY;
                importance_map[i][j] = ocup_map[i][j].weight;
            }
        }
        ocup_map[1][1] = TileContents.EMPTY;
        importance_map[1][1] = TileContents.EMPTY.weight;

        return new Map(importance_map,ocup_map);
    }
}
