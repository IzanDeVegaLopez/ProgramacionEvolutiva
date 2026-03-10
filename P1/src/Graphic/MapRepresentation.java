package Graphic;

import Graphic.MappingUtils.CompoundColor;
import Mapas.Map;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;


import Mapas.mapReader;
import utils.Vector2;
//import fitness.FitnessReturnClass;

public class MapRepresentation extends MyPanel{
    JPanel [][] myTiles;
    public Map m;
    Color[] colorPerCamera;
    MapRepresentation(int mapID){
        super(255,255,255);
        m = mapReader.readMap(mapID);
        int x = m.ocupiedTiles.length,y=m.ocupiedTiles[0].length;
        this.setLayout(new GridLayout(y, x));
        int xx=300; int yy=300;
        this.setMaximumSize(new Dimension(xx,yy));
        this.setMinimumSize(new Dimension(xx,yy));
        myTiles = new JPanel[x][y];
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                //Si esta ocupada pintala negra
                int val = m.ocupiedTiles[j][i] ? 0 : 255;
                myTiles[j][i] = new MyPanel(val);
                Border mborder = BorderFactory.createLineBorder(mapReader.colorPerValue[m.importanceMap[j][i]/5]);
                myTiles[j][i].setBorder(mborder);
                this.add(myTiles[j][i]);
            }
        }
    }
    public void DrawPaths(Vector<Vector2>[] paths){
        CompoundColor[][] tileColors = new CompoundColor[myTiles.length][myTiles[0].length];
        for (int i = 0; i<myTiles.length;i++){
            for (int j = 0; j<myTiles[0].length;j++){
                tileColors[i][j] = new CompoundColor(myTiles[i][j].getBackground());
            }
        }

        int colorIdx = 0;
        for (Vector<Vector2> path : paths) {
            if (path.elementAt(0).x == (-1) || path.elementAt(0).y == (-1)) continue;
            for (Vector2 step : path) {
                tileColors[step.y][step.x].addColor(mapReader.PathColors[colorIdx]);
            }
            colorIdx++;
        }

        for (int i = 0; i<myTiles.length;i++){
            for (int j = 0; j<myTiles[0].length;j++){
                myTiles[i][j].setBackground(tileColors[i][j].final_color);
            }
        }
    }
/*
    public void putAllBinCameras(FitnessReturnClass frc){
        emptyCamerasOnMap();
        int i = 0;
        for(ArrayList<int[]> arr : frc.tilesInCameraI){
            putBinCamera(arr,i);
            ++i;
        }
    }

    void emptyCamerasOnMap(){
        for(int i = 0; i < myTiles.length; ++i){
            for(int j = 0; j < myTiles[0].length; ++j){
                myTiles[i][j].setBackground(m.ocupiedTiles[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }

    void putBinCamera(ArrayList<int[]> listTiles, int cameraNumber){
        for(int i = 0; i < listTiles.size(); ++i) {
            myTiles[listTiles.get(i)[0]][listTiles.get(i)[1]].setBackground(colorPerCamera[cameraNumber]);
        }
    }
 */
}
