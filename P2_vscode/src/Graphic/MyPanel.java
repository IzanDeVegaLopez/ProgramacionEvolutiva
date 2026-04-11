package Graphic;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel(int r, int g, int b){
        setBackground(new Color(r,g,b));
        setVisible(true);
    }
    public MyPanel(int n){
        setBackground(new Color(n,n,n));
        setVisible(true);
    }
    public MyPanel(){
        setBackground(new Color(255,255,255));
        setVisible(true);
    }
}