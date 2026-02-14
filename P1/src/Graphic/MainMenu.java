package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.HashMap;

import GeneticAlgorithm.*;

import org.math.plot.*;

public class MainMenu extends MyFrame{
    int boxSizeY = 20;
    int labelSizeX = 175;
    int menuDesplegableSizeX = 100;
    MapRepresentation[] mapRepresentation;
    Plot2DPanel plot2D;
    NumericField nGensField;
    NumericField nIndInGenField;
    NumericField elitismRatio;
    JCheckBox elitismBox;
    JCheckBox ponderadoBox;
    NumericField mutationProbability;
    NumericField crossProbability;
    JComboBox selectionTypeComboBox;
    JComboBox crossMethodComboBox;
    JComboBox mutationMethodComboBox;
    JComboBox codificationTypeComboBox;
    JLabel maxValue;
    JLabel enforcingValue;
    JTabbedPane mapsTabs;

    HashMap<String,Integer> selectionHash;
    HashMap<String,Integer> codeHash;
    HashMap<String,Integer> mutationHash;
    HashMap<String,Integer> crossHash;


    public MainMenu(int x,int y){
        super(x,y);

        initHashMaps();

        MyPanel parent = new MyPanel();
        parent.setSize(300,300);
        parent.setLayout(new BoxLayout(parent,BoxLayout.X_AXIS));
        //controls
        parent.add(createControlsMenu());
        //graphics
        parent.add(createGraphicsMenu());

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
        crossHash.put("Aritmético (solo REAL)",2);
        crossHash.put("BLX-alpha (solo REAL)",3);

        mutationHash = new HashMap<>();
        mutationHash.put("A nivel de bit", 0);
        mutationHash.put("Gaussiana (solo REAL)",1);

    }

    JPanel createControlsMenu(){
        MyPanel pan = new MyPanel();
        pan.setSize(100,100);
        pan.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        String[] s = new String[]{"MUSEO","PASILLOS","SUPERMERCADO"};
        mapsTabs = new JTabbedPane();
        mapRepresentation = new MapRepresentation[3];
        for(int i = 0; i < s.length; ++i){
            mapRepresentation[i] = createMap(i);
            mapsTabs.addTab(s[i],mapRepresentation[i]);
        }
        pan.add(mapsTabs);

        pan.add(createAllMenusDesplegables());

        pan.add(ponderadoBox = createCheckBox("Método Ponderado"));
        pan.add(elitismBox = createCheckBox("Usar elitismo"));
        MyPanel elitismPanel = new MyPanel();
        elitismPanel.setLayout(new BoxLayout(elitismPanel,BoxLayout.X_AXIS));
        elitismPanel.add(createLabel("Elitismo (%): "));
        elitismPanel.add(elitismRatio = createNumericField(20));
        pan.add(elitismPanel);

        return pan;
    }

    MapRepresentation createMap(int mapId){
        MapRepresentation m = new MapRepresentation(mapId);
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
        p2.add(crossMethodComboBox= createMenuDesplegable(new String[]{"Monopunto", "Uniforme", "Aritmético (solo REAL)", "BLX-alpha (solo REAL)"}));
        //Mutación
        MyPanel p3 = new MyPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.X_AXIS));
        p3.add(createLabel("Mutación"));
        p3.add(mutationMethodComboBox = createMenuDesplegable(new String[]{"A nivel de bit", "Gaussiana (solo REAL)"}));

        //Tamaño Población
        MyPanel p4 = new MyPanel();
        p4.setLayout(new BoxLayout(p4,BoxLayout.X_AXIS));
        p4.add(createLabel("Tamaño Población"));
        p4.add(nIndInGenField = createNumericField(100));
        //Generaciones
        MyPanel p5 = new MyPanel();
        p5.setLayout(new BoxLayout(p5,BoxLayout.X_AXIS));
        p5.add(createLabel("Número generaciones"));
        p5.add(nGensField = createNumericField(200));

        //Porcentaje Mutación
        MyPanel p6 = new MyPanel();
        p6.setLayout(new BoxLayout(p6,BoxLayout.X_AXIS));
        p6.add(createLabel("Porcentaje mutación (%)"));
        p6.add(mutationProbability= createNumericField(5));

        //Porcentaje Cruce
        MyPanel p7 = new MyPanel();
        p7.setLayout(new BoxLayout(p7,BoxLayout.X_AXIS));
        p7.add(createLabel("Porcentaje cruce (%)"));
        p7.add(crossProbability= createNumericField(60));

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

    JCheckBox createCheckBox(String s){
        JCheckBox chckBx = new JCheckBox(s);
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

    JPanel createGraphicsMenu(){
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        plot2D = new Plot2DPanel();
        //plot.setSize(100,100);
        plot2D.addLegend("SOUTH");
        plot2D.setVisible(true);
        pan.add(plot2D, BorderLayout.CENTER);

        JPanel infoPan = new JPanel();
        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.X_AXIS));

        JPanel L = new JPanel();
        L.setLayout(new BoxLayout(L, BoxLayout.X_AXIS));
        L.add(createLabel("Presión Selectiva: "));
        L.add(enforcingValue = createLabel("1"));

        JPanel R = new JPanel();
        R.setLayout(new BoxLayout(R, BoxLayout.X_AXIS));
        R.add(createLabel("Mejor Resultado: "));
        R.add(maxValue = createLabel("0"));

        infoPan.add(L); infoPan.add(R);
        pan.add(infoPan, BorderLayout.NORTH);

        JPanel butPan = new JPanel();
        //infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.X_AXIS));
        Button but = new Button("Start");
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneticAlgorithmParameters g = new GeneticAlgorithmParameters();
                g.plot2d = plot2D;
                g.m = mapRepresentation[mapsTabs.getSelectedIndex()];
                //
                g.nGen = Integer.parseInt(nGensField.textField.getText());
                g.nIndInGen = Integer.parseInt(nIndInGenField.textField.getText());
                g.crossProbability = Float.parseFloat(crossProbability.textField.getText()) / 100.0f;
                g.mutationprobability = Float.parseFloat(mutationProbability.textField.getText()) / 100.0f;
                //
                g.crossType = crossHash.get(crossMethodComboBox.getSelectedItem().toString());
                g.selectionType = selectionHash.get(selectionTypeComboBox.getSelectedItem().toString());
                g.mutationType = mutationHash.get(mutationMethodComboBox.getSelectedItem().toString());
                Integer codeType = codeHash.get(codificationTypeComboBox.getSelectedItem().toString());
                g.isPonderado = ponderadoBox.isSelected();
                g.elite_ratio = elitismBox.isSelected() ? Float.parseFloat(elitismRatio.textField.getText())/100.0f : 0;
                float[] Enforcing_n_Max = codeType==0 ?
                        new BINGeneticAlgorithm(g).getMidSelectionEnforcer_n_getMax() :
                        new REALGeneticAlgorithm(g).getMidSelectionEnforcer_n_getMax();
                maxValue.setText(""+Enforcing_n_Max[1]);
                enforcingValue.setText(""+Enforcing_n_Max[0]);

            }
        });
        but.setSize(new Dimension(100,100));

        butPan.add(but);
        pan.add(butPan, BorderLayout.SOUTH);

        return pan;
    }

}
