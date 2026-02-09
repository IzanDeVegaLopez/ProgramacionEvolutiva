package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public int totalCameras;
    //[Camera][tileNumber][0==x,1==y]
    public ArrayList<ArrayList<int[]>> tilesInCameraI;
    public FitnessReturnClass(){
        totalCameras = 0;
        tilesInCameraI = new ArrayList<ArrayList<int[]>>(0);
    }
}
