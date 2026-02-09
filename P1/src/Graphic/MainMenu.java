package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.lang.*;

import org.math.plot.*;

public class MainMenu extends MyFrame{
    public MainMenu(int x,int y){
        super(x,y);

        MyPanel parent = new MyPanel(255,255,0);
        parent.setSize(300,300);
        parent.setLayout(new BoxLayout(parent,BoxLayout.X_AXIS));
        //controls
        parent.add(createControlsMenu());
        //graphic
        parent.add(createGraphicsMenu());

        add(parent);
        pack();
        setSize(800,600);
    }

    JPanel createControlsMenu(){
        MyPanel pan = new MyPanel(255,0,0);
        pan.setSize(100,100);
        pan.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        pan.add(createCheckBox("Elitism"));
        pan.add(createCheckBox("second checkbox"));
        pan.add(createCheckBox("third checkbox"));


        return pan;
    }

    Checkbox createCheckBox(String s){
        Checkbox chckBx = new Checkbox(s);
        chckBx.setSize(30,40);
        chckBx.setBackground(new Color(0,255,255));
        return chckBx;
    }

    Plot2DPanel createGraphicsMenu(){
        Plot2DPanel plot = new Plot2DPanel();
        plot.setSize(100,100);
        plot.addLegend("SOUTH");
        double test[][] ={
                {0,1,2,3,4,5,2},
                {0,1,2,3,5,6,7}
        };
        plot.addLinePlot("my plot", test);
        //JLabel label = new JLabel("Hello, GUI World!", SwingConstants.CENTER);
        plot.setVisible(true);
        return plot;
    }

}
