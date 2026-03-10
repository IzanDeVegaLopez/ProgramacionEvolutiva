package Mapas;

import utils.Vector2;

import java.util.BitSet;
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
        taken_base = new BitSet(ocup.length*ocup[0].length);
        int n = 0;
        for (boolean[] booleans : ocup) {
            for (boolean b : booleans) {
                taken_base.set(n, b);
                n++;
            }
        }
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

    /**
     * Selects a number of random unique empty tiles from the map.
     * @param n The number of random empty tiles to return.
     * @return `n` random unique empty tiles.
     */
    public Vector2[] getRandomTiles(int n){
        Vector2[] results = new Vector2[n];
        BitSet taken_temp = (BitSet) taken_base.clone();
        for (int i = 0; i<n; i++){
            int random_idx = (int) (Math.random() * taken_temp.size());
            int next_non_visited = taken_temp.nextClearBit(random_idx);
            if(next_non_visited >= taken_temp.size()){
                next_non_visited = taken_temp.nextClearBit(0);
            }
            results[i] = new Vector2(next_non_visited % ocupiedTiles.length,
                    next_non_visited / ocupiedTiles.length);
            taken_temp.set(next_non_visited);
        }
        return results;
    }
}
