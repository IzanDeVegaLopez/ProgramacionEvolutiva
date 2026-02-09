package Graphic;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel(int r, int g, int b){
        setBackground(new Color(r,g,b));
        setVisible(true);
    }
}