package Mapas;

public class Map {
    int [][] importanceMap;
    boolean[][] ocupiedTiles;
    int nCamaras;
    int visionRange;
    float apertureAngle;
    Map(int[][] imp, boolean [][]ocup, int nCam, int visRng, float angle){
        importanceMap = imp;
        ocupiedTiles = ocup;
        nCamaras = nCam;
        visionRange = visRng;
        apertureAngle = angle;
    }

}
