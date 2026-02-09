package Mapas;

public class Map {
    public int [][] importanceMap;
    public boolean[][] ocupiedTiles;
    public int nCamaras;
    public int visionRange;
    public float apertureAngle;
    Map(int[][] imp, boolean [][]ocup, int nCam, int visRng, float angle){
        importanceMap = imp;
        ocupiedTiles = ocup;
        nCamaras = nCam;
        visionRange = visRng;
        apertureAngle = angle;
    }
    public boolean validTile(int x, int y){
        return x >= 0 && y >= 0 &&
               x < ocupiedTiles.length && y < ocupiedTiles[0].length &&
               !ocupiedTiles[x][y];
    }

}
