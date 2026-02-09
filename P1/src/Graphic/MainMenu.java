package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.lang.*;

import org.math.plot.*;

public class MainMenu extends MyFrame{
    int boxSizeY = 150;
    int labelSizeX = 460;
    int menuDesplegableSizeX = 300;

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

        pan.add(createAllMenusDesplegables());

        pan.add(createCheckBox("Elitismo"));

        return pan;
    }

    JPanel createAllMenusDesplegables(){
        MyPanel panelConjunto = new MyPanel(150,150,150);
        panelConjunto.setLayout(new BoxLayout(panelConjunto, BoxLayout.X_AXIS));
        panelConjunto.setMaximumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY));
        panelConjunto.setMinimumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY));

        MyPanel panL = new MyPanel(255,255,255);
        MyPanel panR = new MyPanel(255,255,255);
        panL.setLayout(new BoxLayout(panL, BoxLayout.Y_AXIS));
        panR.setLayout(new BoxLayout(panR, BoxLayout.Y_AXIS));

        //Codificación
        panL.add(createLabel("Codificación"));
        panR.add(createMenuDesplegable(new String[]{"Binario", "Punto flotante"}));
        //método de Selección
        panL.add(createLabel("Método de Selección"));
        panR.add(createMenuDesplegable(new String[]{"Ruleta", "Torneo", "Estocástico", "Truncamiento", "Restos"}));
        //Operadores de cruce
        panL.add(createLabel("Operadores de cruce"));
        panR.add(createMenuDesplegable(new String[]{"Monopunto", "Uniforme"}));
        //Mutación
        panL.add(createLabel("Mutación"));
        panR.add(createMenuDesplegable(new String[]{"A nivel de bit"}));

        panelConjunto.add(panL);
        panelConjunto.add(panR);
        return panelConjunto;
    }

    Checkbox createCheckBox(String s){
        Checkbox chckBx = new Checkbox(s);
        chckBx.setSize(30,40);
        chckBx.setBackground(new Color(0,255,255));
        chckBx.setMaximumSize(new Dimension(labelSizeX+menuDesplegableSizeX, boxSizeY));
        chckBx.setMinimumSize(new Dimension(labelSizeX+menuDesplegableSizeX, boxSizeY));
        return chckBx;
    }

    JLabel createLabel(String s){
        JLabel l = new JLabel(s);
        l.setMaximumSize(new Dimension(labelSizeX, boxSizeY));
        l.setMinimumSize(new Dimension(labelSizeX, boxSizeY));
        return l;
    }

    JComboBox createMenuDesplegable(String[] s){
        JComboBox cmb = new JComboBox<>(s);
        cmb.setMaximumSize(new Dimension(menuDesplegableSizeX, boxSizeY));
        cmb.setMinimumSize(new Dimension(menuDesplegableSizeX, boxSizeY));
        return cmb;
    }

    Plot2DPanel createGraphicsMenu(){
        Plot2DPanel plot = new Plot2DPanel();
        //plot.setSize(100,100);
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
