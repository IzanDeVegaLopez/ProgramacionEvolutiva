package Graphic;

import Graphic.MappingUtils.CompoundColor;
import Mapas.Map;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;


import Mapas.TileContents;
import Mapas.mapReader;
import Mapas.mapStorage;
import utils.Vector2;
//import fitness.FitnessReturnClass;

public class MapRepresentation extends MyPanel{
    JPanel [][] myTiles;
    public Map m;
    Color[] colorPerCamera;
    MapRepresentation(int mapID){
        super(255,255,255);
        m = mapStorage.get_map(mapID);
        int y = m.ocupiedTiles.length, x=m.ocupiedTiles[0].length;
        this.setLayout(new GridLayout(y, x));
        int xx=300; int yy=300;
        this.setMaximumSize(new Dimension(xx,yy));
        this.setMinimumSize(new Dimension(xx,yy));
        myTiles = new JPanel[y][x];
        for(int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                //Si esta ocupada pintala negra
                switch (m.ocupiedTiles[i][j]){
                    case TileContents.WALL:
                        myTiles[i][j] = new MyPanel(50);
                        break;
                    case TileContents.SAND:
                        myTiles[i][j] = new MyPanel(215, 180, 125);
                        break;
                    default:
                        myTiles[i][j] = new MyPanel(255);
                        break;
                }
                Border mborder = BorderFactory.createLineBorder(new Color(50,50,50), 1);
                myTiles[i][j].setBorder(mborder);
                this.add(myTiles[i][j]);
            }
        }
    }
    public void DrawPaths(Vector<Vector2>[] paths){
        CompoundColor[][] tileColors = new CompoundColor[myTiles.length][myTiles[0].length];
        for (int i = 0; i<myTiles.length;++i){
            for (int j = 0; j<myTiles[0].length;++j){
                tileColors[i][j] = new CompoundColor(myTiles[i][j].getBackground());
            }
        }

        //IO.print("NEW DRAWING STARTED\n");
        int colorIdx = 0;
        for (Vector<Vector2> path : paths) {
            for (Vector2 step : path) {
                tileColors[step.y][step.x].addColor(mapReader.PathColors[colorIdx]);
                //IO.print("("+step.x+","+step.y+"), ");
            }//IO.print("'\n");
            colorIdx++;
        }
        //IO.print('\n');

        for (int i = 0; i<myTiles.length;i++){
            for (int j = 0; j<myTiles[0].length;j++){
                myTiles[i][j].setBackground(tileColors[i][j].final_color);
            }
        }
    }
    public void DrawPoints(Vector2[] points){
        int i = 0;
        for (Vector2 point : points){
            CircularPanel mark = new CircularPanel(new Color(100,150,200),Integer.toString(i),1.0f,1.0f, 20);
            mark.setMinimumSize(new Dimension(20,20));
            myTiles[point.y][point.x].add(mark);
            ++i;
        }
        revalidate();
        repaint();
    }
    public void DrawPoints(Vector2[] points, Color c, int rad){
        int i = 0;
        for (Vector2 point : points){
            CircularPanel mark = new CircularPanel(c," ",1.0f,1.0f, 20);
            mark.setMinimumSize(new Dimension(rad,rad));
            myTiles[point.y][point.x].add(mark);
            ++i;
        }
        revalidate();
        repaint();
    }
    public void DrawSamples(){
        for (int i = 0; i<myTiles.length;++i) {
            for (int j = 0; j < myTiles[0].length; ++j) {
                if (m.ocupiedTiles[i][j] == TileContents.SAMPLE) {
                    CircularPanel mark = new CircularPanel(new Color(225, 175, 75), " ", 1.0f, 1.0f, 20);
                    mark.setMinimumSize(new Dimension(20, 20));
                    mark.setMaximumSize(new Dimension(20, 20));
                    myTiles[i][j].add(mark);
                }
            }
        }
        revalidate();
        repaint();
    }
    public void WipeMapBackground(){
        for(int i = 0; i < myTiles.length; ++i){
            for(int j = 0; j < myTiles[0].length; ++j){
//                myTiles[i][j].removeAll();
                myTiles[i][j].setBackground(m.ocupiedTiles[i][j] == TileContents.WALL ? Color.BLACK : Color.WHITE);
            }
        }
        revalidate();
        repaint();
    }
    public void WipeMap(){
        for(int i = 0; i < myTiles.length; ++i){
            for(int j = 0; j < myTiles[0].length; ++j){
                myTiles[i][j].removeAll();
                switch (m.ocupiedTiles[i][j]){
                    case TileContents.WALL:
                        myTiles[i][j].setBackground(new Color(50,50,50));
                        break;
                    case TileContents.SAND:
                        myTiles[i][j].setBackground(new Color(215, 180, 125));
                        break;
                    default:
                        myTiles[i][j].setBackground(new Color(255,255,255));
                        break;
                }
            }
        }
        revalidate();
        repaint();
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
