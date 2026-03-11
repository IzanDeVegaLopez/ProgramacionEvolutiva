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
    public Vector2[][] previous;
    //Last position is reserved for start position
    public Vector2[] interest_points;
    BitSet taken_base;

    public int penalty=100;
    Map(int[][] imp, boolean [][]ocup){
        importanceMap = imp;
        ocupiedTiles = ocup;
        tainted = new boolean[ocup.length][ocup[0].length];
        previous = new Vector2[ocup.length][ocup[0].length];
    }
    public boolean validTile(int x, int y){
        return x >= 0 && y >= 0 &&
               x < ocupiedTiles.length && y < ocupiedTiles[0].length &&
               !ocupiedTiles[x][y];
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
    public void resetPrevious(){
        for(int i = 0; i < ocupiedTiles.length; ++i){
            for(int j = 0; j < ocupiedTiles[0].length; ++j){
                previous[i][j] = new Vector2(-1,-1);
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

        Random rand = new Random(seed);
        Vector2[] results = new Vector2[n+1];
        results[results.length-1] = new Vector2(1,1);
        tainted[1][1] = true;
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

        return interest_points = results.clone();
    }
}
