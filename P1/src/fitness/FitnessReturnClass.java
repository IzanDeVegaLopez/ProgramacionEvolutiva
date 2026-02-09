package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public int totalTiles;
    //[Camera][tileNumber][0==x,1==y]
    public ArrayList<ArrayList<int[]>> tilesInCameraI;
    public FitnessReturnClass(){
        totalTiles = 0;
        tilesInCameraI = new ArrayList<ArrayList<int[]>>(0);
    }
}
