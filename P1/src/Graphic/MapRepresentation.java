package Graphic;

import Mapas.Map;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import Mapas.mapReader;

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
            colorPerCamera[0] = new Color(
                    (int)Math.floor(Math.random()*255),
                    (int)Math.floor(Math.random()*255),
                    (int)Math.floor(Math.random()*255)
            );
        }
    }

    void putBinCamera(int x, int y, int cameraNumber){
        int[][]dir = new int[][]{{1,0},{0,1}, {-1,0}, {0,-1}};
        if(m.ocupiedTiles[x][y]) return;
        myTiles[x][y].setBackground(colorPerCamera[cameraNumber]);
        for(int[] delta : dir){
            for(int i = 1; i < m.visionRange && !m.ocupiedTiles[x+delta[0]*i][y+delta[1]*i]; ++i){
                myTiles[x+delta[0]*i][y+delta[1]*i].setBackground(colorPerCamera[cameraNumber]);
            }
        }
    }
}
