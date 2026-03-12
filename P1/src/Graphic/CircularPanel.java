package Graphic;

import utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class CircularPanel extends JPanel {
    float proportional_width;
    float proportional_height;
    Color c;
    public CircularPanel(Color _c,String s, float _proportional_width, float _proportional_height, int font_size){
        c=_c;
        //setLayout(new GridBagLayout());
        JLabel txt = new JLabel();
        //GridBagConstraints gc = new GridBagConstraints();
        //gc.gridy = 0;
        txt.setText(s);
        txt.setFont(new Font("Monospaced", Font.BOLD, font_size));
        add(txt);//, gc);
        setBackground(c);
        proportional_width = _proportional_width;
        proportional_height = _proportional_height;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(c);
        //int max = Math.max((int)(proportional_width*g.getClipBounds().width), (int)(proportional_height*g.getClipBounds().height));
        //g.fillOval(0, 0, max, max);
            g.fillOval(0,0,(int)(proportional_width*g.getClipBounds().width), (int)(proportional_height*g.getClipBounds().height));
    }
}
