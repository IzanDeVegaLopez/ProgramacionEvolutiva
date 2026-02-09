package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

import GeneticAlgorithm.*;
import Mapas.*;
import codification.*;

import fitness.fitnessFunctions;
import org.math.plot.*;

import static fitness.fitnessFunctions.getBinFitness;

public class MainMenu extends MyFrame{
    int boxSizeY = 50;
    int labelSizeX = 460;
    int menuDesplegableSizeX = 300;
    MapRepresentation mapRepresentation;
    Plot2DPanel plot2D;
    NumericField nGensField;
    NumericField nIndInGenField;

    public MainMenu(int x,int y){
        super(x,y);

        MyPanel parent = new MyPanel(255,255,0);
        parent.setSize(300,300);
        parent.setLayout(new BoxLayout(parent,BoxLayout.X_AXIS));
        //controls
        parent.add(createControlsMenu());
        //graphic
        plot2D = createGraphicsMenu();
        parent.add(plot2D);

        add(parent);
        pack();
        setSize(800,600);
    }

    JPanel createControlsMenu(){
        MyPanel pan = new MyPanel(255,0,0);
        pan.setSize(100,100);
        pan.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        mapRepresentation = createMap(mapReader.MUSEO);
        pan.add(mapRepresentation);

        pan.add(createAllMenusDesplegables());

        pan.add(createCheckBox("Elitismo"));

        Button but = new Button("Start");
        but.addActionListener(new ActionListener() {
          @Override
            public void actionPerformed(ActionEvent e) {
              GeneticAlgorithmParameters g = new GeneticAlgorithmParameters();
              g.plot2d = plot2D;
              g.m = mapRepresentation.m;
              g.nGen = Integer.parseInt(nGensField.textField.getText());
              g.nIndInGen = Integer.parseInt(nIndInGenField.textField.getText());
              GeneticAlgorithm ga = new GeneticAlgorithm(g);
            }
        });
        pan.add(but);

        return pan;
    }

    MapRepresentation createMap(int mapId){
        MapRepresentation m = new MapRepresentation(mapId);
        //m.putBinCamera(1,1,0);
        codificacion_binaria cod = codificacion_binaria.getTestCod();
        m.putAllBinCameras(fitnessFunctions.getBinFitness(m.m, cod));
        return m;
    }

    JPanel createAllMenusDesplegables(){
        int nElems = 8;

        MyPanel panelConjunto = new MyPanel(150,150,150);
        panelConjunto.setLayout(new BoxLayout(panelConjunto, BoxLayout.X_AXIS));
        panelConjunto.setMaximumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY*nElems));
        panelConjunto.setMinimumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY*nElems));

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

        //Tamaño Población
        panL.add(createLabel("Tamaño Población"));
        panR.add(nIndInGenField = createNumericField(20));
        //Generaciones
        panL.add(createLabel("Número generaciones"));
        panR.add(nGensField = createNumericField(100));

        //Porcentaje Mutación
        panL.add(createLabel("Porcentaje mutación (%)"));
        panR.add(createNumericField(5));

        //Porcentaje Cruce
        panL.add(createLabel("Porcentaje cruce (%)"));
        panR.add(createNumericField(30));

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

    NumericField createNumericField(int startVal){
        NumericField t = new NumericField(startVal);
        t.setMaximumSize(new Dimension(labelSizeX+menuDesplegableSizeX, boxSizeY));
        t.setMinimumSize(new Dimension(labelSizeX+menuDesplegableSizeX, boxSizeY));
        return t;
    }

    Plot2DPanel createGraphicsMenu(){
        Plot2DPanel plot = new Plot2DPanel();
        //plot.setSize(100,100);
        plot.addLegend("SOUTH");
        plot.setVisible(true);
        return plot;
    }

}
