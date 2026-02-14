package fitness;

import java.util.ArrayList;

public class FitnessReturnClass {
    public int totalValue;
    public int totalNPenalties;
    //[Camera][tileNumber][0==x,1==y]
    public ArrayList<ArrayList<int[]>> tilesInCameraI;
    public FitnessReturnClass(){
        totalValue = 0;
        tilesInCameraI = new ArrayList<ArrayList<int[]>>(0);
    }
}
