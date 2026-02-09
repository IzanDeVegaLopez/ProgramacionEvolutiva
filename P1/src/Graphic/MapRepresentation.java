package Graphic;

import Mapas.Map;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MapRepresentation extends MyPanel{
    MapRepresentation(Map[] m){
        super(255,255,255);
        this.setLayout(new GridLayout(m[0].ocupiedTiles[0].length, m[0].ocupiedTiles.length));
        for(int i = 0; i < m[0].ocupiedTiles[0].length; ++i){
            for(int j = 0; j < m[0].ocupiedTiles.length; ++j){
                //Si esta ocupada pintala negra
                int val = m[0].ocupiedTiles[j][i] ? 0 : 255;
                MyPanel pan =new MyPanel(val);
                Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY);
                pan.setBorder(blackline);
                this.add(pan);
            }
        }
    }
}
