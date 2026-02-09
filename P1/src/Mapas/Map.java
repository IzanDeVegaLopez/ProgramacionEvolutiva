package Mapas;

public class Map {
    public int [][] importanceMap;
    public boolean[][] ocupiedTiles;
    //Para llevar la cuenta de cuales he tocado ya con una cámara en este recorrido
    public boolean[][] tainted;
    public int nCamaras;
    public int visionRange;
    public float apertureAngle;
    Map(int[][] imp, boolean [][]ocup, int nCam, int visRng, float angle){
        importanceMap = imp;
        ocupiedTiles = ocup;
        nCamaras = nCam;
        visionRange = visRng;
        apertureAngle = angle;
        tainted = new boolean[ocup.length][ocup[0].length];
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

}
