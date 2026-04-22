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
    MyPanel [][] myTiles;
    public Map m;
    MapRepresentation(int mapID){
        super(255,255,255);
        m = mapStorage.get_map(mapID);
        int y = m.ocupiedTiles.length, x=m.ocupiedTiles[0].length;
        this.setLayout(new GridLayout(y, x));
        int xx=300; int yy=300;
        this.setMaximumSize(new Dimension(xx,yy));
        this.setMinimumSize(new Dimension(xx,yy));
        myTiles = new MyPanel[y][x];
        Border mborder = BorderFactory.createLineBorder(new Color(50,50,50), 1);
        for(int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                myTiles[i][j] = new MyPanel();
                //Si esta ocupada pintala negra
                myTiles[i][j].setBorder(mborder);
                this.add(myTiles[i][j]);
            }
        }
        recolor_map(new Vector<Vector2>(0), new Vector2(1,1));
    }
    public void recolor_map(Vector<Vector2> marked_tiles, Vector2 final_tile){
        int y = m.ocupiedTiles.length, x=m.ocupiedTiles[0].length;
        for(int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                myTiles[i][j].removeAll();
                //Si esta ocupada pintala negra
                switch (m.ocupiedTiles[i][j]){
                    case TileContents.WALL:
                        myTiles[i][j].setColor(150,0,0);
                        break;
                    case TileContents.SAND:
                        myTiles[i][j].setColor(215, 180, 125);
                        break;
                    case TileContents.SAMPLE:
                        add_circle_to_tile(new Vector2(j,i), new Color(225,175,75), new Vector2(20,20));
                    case TileContents.EMPTY:
                        myTiles[i][j].setColor(0);
                        break;
                }
            }
        }

        for(Vector2 v : marked_tiles){
            add_circle_to_tile(v, new Color(200,255,255,127), new Vector2(30,30));
        }
        add_circle_to_tile(final_tile, new Color(0,255,255), new Vector2(30,30));

        revalidate();
        repaint();
    }

    public void add_circle_to_tile(Vector2 tile, Color c, Vector2 size){
        myTiles[tile.y][tile.x].removeAll();
        CircularPanel mark = new CircularPanel(c, " ", 1.0f, 1.0f, 20);
        mark.setMinimumSize(new Dimension(size.x, size.y));
        mark.setMaximumSize(new Dimension(size.x, size.y));
        myTiles[tile.y][tile.x].add(mark);
    }
}
