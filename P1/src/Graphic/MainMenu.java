package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.HashMap;

import GeneticAlgorithm.*;
import Mapas.*;
import codification.*;

import fitness.fitnessFunctions;
import org.math.plot.*;

import static fitness.fitnessFunctions.getBinFitness;

public class MainMenu extends MyFrame{
    int boxSizeY = 50;
    int labelSizeX = 175;
    int menuDesplegableSizeX = 100;
    MapRepresentation mapRepresentation;
    Plot2DPanel plot2D;
    NumericField nGensField;
    NumericField nIndInGenField;
    NumericField mutationProbability;
    NumericField crossProbability;
    JComboBox selectionTypeComboBox;
    JComboBox crossMethodComboBox;
    JComboBox mutationMethodComboBox;
    JComboBox codificationTypeComboBox;

    HashMap<String,Integer> selectionHash;
    HashMap<String,Integer> codeHash;
    HashMap<String,Integer> mutationHash;
    HashMap<String,Integer> crossHash;


    public MainMenu(int x,int y, int mapNumber){
        super(x,y);

        mapRepresentation = createMap(mapNumber);

        initHashMaps();

        MyPanel parent = new MyPanel();
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
    void initHashMaps(){
        codeHash = new HashMap<>();
        codeHash.put("Binario", 0);
        codeHash.put("Punto flotante", 1);

        selectionHash = new HashMap<>();
        selectionHash.put("Ruleta",0);
        selectionHash.put("Torneo",1);
        selectionHash.put("Estocástico",2);
        selectionHash.put("Truncamiento",3);
        selectionHash.put("Restos",4);

        crossHash = new HashMap<>();
        crossHash.put("Monopunto",0);
        crossHash.put("Uniforme",1);

        mutationHash = new HashMap<>();
        mutationHash.put("A nivel de bit", 0);

    }

    JPanel createControlsMenu(){
        MyPanel pan = new MyPanel();
        pan.setSize(100,100);
        pan.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        pan.add(mapRepresentation);

        pan.add(createAllMenusDesplegables());

        pan.add(createCheckBox("Elitismo"));

        Button but = new Button("Start");
        but.addActionListener(new ActionListener() {
          @Override
            public void actionPerformed(ActionEvent e) {
              GeneticAlgorithmParameters g = new GeneticAlgorithmParameters();
              g.plot2d = plot2D;
              g.m = mapRepresentation;
              //
              g.nGen = Integer.parseInt(nGensField.textField.getText());
              g.nIndInGen = Integer.parseInt(nIndInGenField.textField.getText());
              g.crossProbability = Float.parseFloat(crossProbability.textField.getText()) / 100.0f;
              g.mutationprobability = Float.parseFloat(mutationProbability.textField.getText()) / 100.0f;
              //
              g.crossType = crossHash.get(crossMethodComboBox.getSelectedItem().toString());
              g.selectionType = selectionHash.get(selectionTypeComboBox.getSelectedItem().toString());
              g.mutationType = mutationHash.get(mutationMethodComboBox.getSelectedItem().toString());
              g.codeType = codeHash.get(codificationTypeComboBox.getSelectedItem().toString());
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

        MyPanel panelConjunto = new MyPanel();
        panelConjunto.setLayout(new BoxLayout(panelConjunto, BoxLayout.Y_AXIS));
        panelConjunto.setMaximumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY*nElems));
        panelConjunto.setMinimumSize(new Dimension(labelSizeX + menuDesplegableSizeX, boxSizeY*nElems));

        //Codificación
        MyPanel p = new MyPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
        p.add(createLabel("Codificación"));
        p.add(codificationTypeComboBox= createMenuDesplegable(new String[]{"Binario", "Punto flotante"}));
        //método de Selección
        MyPanel p1 = new MyPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.X_AXIS));
        p1.add(createLabel("Método de Selección"));
        p1.add(selectionTypeComboBox= createMenuDesplegable(new String[]{"Ruleta", "Torneo", "Estocástico", "Truncamiento", "Restos"}));
        //Operadores de cruce
        MyPanel p2 = new MyPanel();
        p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
        p2.add(createLabel("Operadores de cruce"));
        p2.add(crossMethodComboBox= createMenuDesplegable(new String[]{"Monopunto", "Uniforme"}));
        //Mutación
        MyPanel p3 = new MyPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.X_AXIS));
        p3.add(createLabel("Mutación"));
        p3.add(mutationMethodComboBox = createMenuDesplegable(new String[]{"A nivel de bit"}));

        //Tamaño Población
        MyPanel p4 = new MyPanel();
        p4.setLayout(new BoxLayout(p4,BoxLayout.X_AXIS));
        p4.add(createLabel("Tamaño Población"));
        p4.add(nIndInGenField = createNumericField(20));
        //Generaciones
        MyPanel p5 = new MyPanel();
        p5.setLayout(new BoxLayout(p5,BoxLayout.X_AXIS));
        p5.add(createLabel("Número generaciones"));
        p5.add(nGensField = createNumericField(100));

        //Porcentaje Mutación
        MyPanel p6 = new MyPanel();
        p6.setLayout(new BoxLayout(p6,BoxLayout.X_AXIS));
        p6.add(createLabel("Porcentaje mutación (%)"));
        p6.add(mutationProbability= createNumericField(5));

        //Porcentaje Cruce
        MyPanel p7 = new MyPanel();
        p7.setLayout(new BoxLayout(p7,BoxLayout.X_AXIS));
        p7.add(createLabel("Porcentaje cruce (%)"));
        p7.add(crossProbability= createNumericField(30));

        panelConjunto.add(p);
        panelConjunto.add(p1);
        panelConjunto.add(p2);
        panelConjunto.add(p3);
        panelConjunto.add(p4);
        panelConjunto.add(p5);
        panelConjunto.add(p6);
        panelConjunto.add(p7);
        return panelConjunto;
    }

    Checkbox createCheckBox(String s){
        Checkbox chckBx = new Checkbox(s);
        chckBx.setSize(30,40);
        chckBx.setBackground(new Color(255,255,255));
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
        t.setMaximumSize(new Dimension(menuDesplegableSizeX, boxSizeY));
        t.setMinimumSize(new Dimension(menuDesplegableSizeX, boxSizeY));
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
