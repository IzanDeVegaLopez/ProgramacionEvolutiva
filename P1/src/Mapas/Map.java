package Mapas;

import utils.Vector2;

public class Map {
    public int [][] importanceMap;
    public boolean[][] ocupiedTiles;
    //Para llevar la cuenta de cuales he tocado ya con una cámara en este recorrido
    public boolean[][] tainted;
    public Vector2[][] previous;
    //Last position is reserved for start position
    public Vector2[] interest_points;

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

}
