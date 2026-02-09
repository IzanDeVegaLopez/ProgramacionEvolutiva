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
    Map m;
    Color[] colorPerCamera;
    MapRepresentation(int mapID){
        super(255,255,255);
        m = mapReader.readMap(mapID);
        int x = m.ocupiedTiles.length,y=m.ocupiedTiles[0].length;
        this.setLayout(new GridLayout(y, x));
        myTiles = new JPanel[x][y];
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                //Si esta ocupada pintala negra
                int val = m.ocupiedTiles[j][i] ? 0 : 255;
                myTiles[j][i] = new MyPanel(val);
                Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);
                myTiles[j][i].setBorder(blackline);
                this.add(myTiles[j][i]);
            }
        }
        colorPerCamera = new Color[m.nCamaras];
        for(int i = 0; i < m.nCamaras; ++i){
            colorPerCamera[i] = new Color(
                    (int)Math.floor(Math.random()*200),
                    (int)Math.floor(Math.random()*200),
                    (int)Math.floor(Math.random()*200)
            );
        }
    }

    public void putAllBinCameras(FitnessReturnClass frc){
        int i = 0;
        for(ArrayList<int[]> arr : frc.tilesInCameraI){
            putBinCamera(arr,i);
            ++i;
        }
    }

    void putBinCamera(ArrayList<int[]> listTiles, int cameraNumber){
        for(int i = 0; i < listTiles.size(); ++i) {
            myTiles[listTiles.get(i)[0]][listTiles.get(i)[1]].setBackground(colorPerCamera[cameraNumber]);
        }
    }
}
