package Mapas;

import utils.Vector2;

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;

public class Map {
    public int [][] importanceMap;
    public boolean[][] ocupiedTiles;
    //Para llevar la cuenta de cuales he tocado ya con una cámara en este recorrido
    public boolean[][] tainted;
    public boolean[][] camera_tiles;
    //Last position is reserved for start position
    public Vector2[] interest_points;
    public int penalty=500;

    Map(int[][] imp, boolean [][]ocup){
        importanceMap = imp;
        ocupiedTiles = ocup;
        tainted = new boolean[ocup.length][ocup[0].length];
        camera_tiles = new boolean[ocup.length][ocup[0].length];
    }
    public boolean has_camera(int x, int y){
        return camera_tiles[y][x];
    }
    public boolean validTile(Vector2 v){
        return validTile(v.x,v.y);
    }
    public double get_tile_cost(Vector2 v){
        return importanceMap[v.y][v.x];
    }
    public boolean validTile(int x, int y){
        return x >= 0 && y >= 0 &&
               x < ocupiedTiles.length && y < ocupiedTiles[0].length &&
               !ocupiedTiles[y][x];
    }
    public boolean usableTile(int x, int y){
        return x >= 0 && y >= 0 &&
                y < ocupiedTiles.length && x < ocupiedTiles[0].length &&
                !ocupiedTiles[y][x] && !tainted[y][x];
    }

    public void resetTainted(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                tainted[i][j] = false;
            }
        }
    }
    public void resetCameraTiles(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                camera_tiles[i][j] = false;
            }
        }
    }

    public void set_tainted_as_walls(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                tainted[i][j] = ocupiedTiles[i][j];
            }
        }
    }

    /**
     * Selects a number of random unique empty tiles from the map.
     * @param n The number of random empty tiles to return.
     * @return `n` random unique empty tiles.
     */
    public Vector2[] getRandomTiles(int n, long seed){
        set_tainted_as_walls();
        resetCameraTiles();

        Random rand = new Random(seed);
        Vector2[] results = new Vector2[n+1];

        int i = 0;
        while(ocupiedTiles[i][i]) ++i;

        results[results.length-1] = new Vector2(i,i);
        tainted[i][i] = true;
        int n_chosen = 0;
        while(n_chosen < n){
            int x = rand.nextInt(ocupiedTiles[0].length);
            int y = rand.nextInt(ocupiedTiles.length);
            if(usableTile(x,y)){
                results[n_chosen] = new Vector2(x,y);
                tainted[y][x] = true;
                camera_tiles[y][x] = true;
                ++n_chosen;
            }
        }

        resetTainted();

        return interest_points = results.clone();
    }
}
