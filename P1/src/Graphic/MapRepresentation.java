package Graphic;

import Mapas.Map;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;


import Mapas.mapReader;
import fitness.FitnessReturnClass;

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
                Border mborder = BorderFactory.createLineBorder(mapReader.colorPerValue[m.importanceMap[i][j]/5]);
                myTiles[j][i].setBorder(mborder);
                this.add(myTiles[j][i]);
            }
        }
        colorPerCamera = new Color[m.nCamaras];
        for(int i = 0; i < m.nCamaras; ++i){
            /*
            colorPerCamera[i] = new Color(
                    (int)Math.floor(Math.random()*100)+100,
                    (int)Math.floor(Math.random()*100)+100,
                    (int)Math.floor(Math.random()*100)+100
            );*/
            int min = 80;
            int r =                     (i<3? (i+1)*min:0)+15;
            int g =                     ((i/3==1)?(i-2)*min:0)+15;
            int b =                     ((i/3==2)?(i-5)*min:0)+15;
            //IO.print("("+r+' '+g+' '+b+')'+'\n');
            colorPerCamera[i] = new Color(r,g,b);
        }
    }

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
}
