package Mapas;

import utils.Vector2;

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;

public class Map {
    public int [][] importanceMap;
    public TileContents[][] ocupiedTiles;
    //Para llevar la cuenta de cuales he tocado ya con una cámara en este recorrido
    public boolean[][] tainted;
    //Last position is reserved for start position
    public int penalty=500;

    public Map(int[][] imp, TileContents[][] ocup){
        importanceMap = imp;
        ocupiedTiles = ocup;
        tainted = new boolean[ocup.length][ocup[0].length];
    }
    public Map(){
        importanceMap = new int[0][0];
        ocupiedTiles = new TileContents[0][0];
        tainted = new boolean[0][0];
    }
    public boolean validTile(Vector2 v){
        return validTile(v.x,v.y);
    }
    public boolean tileWithinBounds(int x, int y){
        return x >= 0 && y >= 0 &&
                y < ocupiedTiles.length && x < ocupiedTiles[0].length;
    }
    public boolean tileWithinBounds(Vector2 pos){
        return tileWithinBounds(pos.x,pos.y);
    }
    public double get_tile_cost(Vector2 v){
        return importanceMap[v.y][v.x];
    }
    public boolean validTile(int x, int y){
        return x >= 0 && y >= 0 &&
               y < ocupiedTiles.length && x < ocupiedTiles[0].length &&
                !is_wall(x,y);
    }
    public boolean usableTile(int x, int y){
        return x >= 0 && y >= 0 &&
                y < ocupiedTiles.length && x < ocupiedTiles[0].length &&
                !is_wall(x,y) && !tainted[y][x];
    }
    public boolean is_wall(int x, int y){
        return ocupiedTiles[y][x] == TileContents.WALL;
    }
    public void resetTainted(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                tainted[i][j] = false;
            }
        }
    }

    public void set_tainted_as_walls(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                tainted[i][j] = ocupiedTiles[i][j] == TileContents.WALL;
            }
        }
    }

    public TileContents get_tile(Vector2 v){
        return get_tile(v.x,v.y);
    }
    public TileContents get_tile(int x, int y){
        return ocupiedTiles[y][x];
    }

    /**
     * Selects a number of random unique empty tiles from the map.
     * @param n The number of random empty tiles to return.
     * @return `n` random unique empty tiles.
     */
    public Vector2[] getRandomTiles(int n, long seed){
        set_tainted_as_walls();

        Random rand = new Random(seed);
        Vector2[] results = new Vector2[n+1];

        int i = 0;
        while(ocupiedTiles[i][i] == TileContents.WALL) ++i;

        results[results.length-1] = new Vector2(i,i);
        tainted[i][i] = true;
        int n_chosen = 0;
        while(n_chosen < n){
            int x = rand.nextInt(ocupiedTiles[0].length);
            int y = rand.nextInt(ocupiedTiles.length);
            if(usableTile(x,y)){
                results[n_chosen] = new Vector2(x,y);
                tainted[y][x] = true;
                ++n_chosen;
            }
        }

        resetTainted();

        return results.clone();
    }
    public boolean set_contents(int x, int y, TileContents contents){
        if (!usableTile(x,y)) return false;
        ocupiedTiles[y][x] = contents;
        importanceMap[y][x] = contents.weight;
        return true;
    }
}
