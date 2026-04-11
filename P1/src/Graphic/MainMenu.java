package Graphic;

import javax.swing.*;
import java.awt.*;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.HashMap;

import GeneticAlgorithm.*;

import Mapas.Map;
import Mapas.TileContents;
import Mapas.mapStorage;
import org.math.plot.*;

import static Mapas.mapGenerator.generateMap;

public class MainMenu extends JFrame{
    int boxSizeY = 20;
    int labelSizeX = 175;
    int menuDesplegableSizeX = 100;
    MapRepresentation[] mapRepresentation;
    Plot2DPanel plot2D;
    NumericField nGensField;
    NumericField nIndInGenField;
    NumericField elitismRatio;
    JCheckBox elitismBox;
    //JCheckBox ponderadoBox;
    NumericField mutationProbability;
    NumericField crossProbability;
    NumericField seedField;
    JComboBox selectionTypeComboBox;
    JComboBox crossMethodComboBox;
    JComboBox mutationMethodComboBox;
    NumericField tiles_of_interest_field;
    NumericField number_of_drones;
    //JComboBox codificationTypeComboBox;
    //JLabel maxValue;
    //JLabel enforcingValue;
    JTabbedPane mapsTabs;

    ScrollTextField logs;

    HashMap<String,Integer> selectionHash;
    HashMap<String,Integer> mutationHash;
    HashMap<String,Integer> crossHash;

    void config(){
         //GraphicsEnvironment graphics =
                //GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice device = graphics.getDefaultScreenDevice();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setUndecorated(true);
        //setResizable(true);
        //device.setFullScreenWindow(this);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(d.width, d.height-40));
        //pack();
    }

    public MainMenu(){
        super("PEV P2");
        config();

        initHashMaps();

        MyPanel parent = new MyPanel();
        parent.setSize(300,300);
        parent.setLayout(new BoxLayout(parent,BoxLayout.X_AXIS));
        //controls
        parent.add(createControlsMenu());
        //graphics
        parent.add(createGraphicsMenu());

        add(parent);

        setVisible(true);
    }
    void initHashMaps(){
        selectionHash = new HashMap<>();
        selectionHash.put("Ruleta",0);
        selectionHash.put("Torneo",1);
        selectionHash.put("Estocástico",2);
        selectionHash.put("Truncamiento",3);
        selectionHash.put("Restos",4);
        selectionHash.put("Ranking",5);

        crossHash = new HashMap<>();
        crossHash.put("CO",0);
        crossHash.put("CX",1);
        crossHash.put("ERX",2);
        crossHash.put("Invented",3);
        crossHash.put("OX",4);
        crossHash.put("OXPP",5);
        crossHash.put("PMX",6);

        mutationHash = new HashMap<>();
        mutationHash.put("Heuristic", 0);
        mutationHash.put("Insertion", 1);
        mutationHash.put("Interchange", 2);
        mutationHash.put("Invented", 3);
        mutationHash.put("Inversion", 4);

    }

    JPanel createControlsMenu(){
        MyPanel pan = new MyPanel();
        pan.setSize(100,100);
        pan.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        String[] s = new String[]{"MAPA 3","MAPA 2","MAPA 1"};
        mapsTabs = new JTabbedPane();
        mapRepresentation = new MapRepresentation[3];
        pan.add(mapsTabs);
        pan.add(createAllMenusDesplegables());

        int height = 15;
        int width = 15;
        for (int i = 0; i<3;i++){
            mapStorage.add_map(generateMap(width,height,Long.parseLong(seedField.textField.getText())+i),i);
        }
        for(int i = s.length-1; i >= 0; --i){
            mapRepresentation[i] = createMap(i);
            mapsTabs.addTab(s[i],mapRepresentation[i]);
        }

        //pan.add(ponderadoBox = createCheckBox("Método Ponderado"));
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
        m.WipeMap();
        return m;
    }

    JPanel createAllMenusDesplegables(){
        int nElems = 8;

        MyPanel panelConjunto = new MyPanel();
        panelConjunto.setLayout(new BoxLayout(panelConjunto, BoxLayout.Y_AXIS));
        panelConjunto.setMaximumSize(new Dimension(labelSizeX + menuDesplegableSizeX + 6*NumericField.x, Math.max(boxSizeY,NumericField.y)*nElems));
        panelConjunto.setMinimumSize(new Dimension(labelSizeX + menuDesplegableSizeX + 6*NumericField.x, Math.max(boxSizeY,NumericField.y)*nElems));

        //método de Selección
        MyPanel p1 = new MyPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.X_AXIS));
        p1.add(createLabel("Método de Selección"));
        p1.add(selectionTypeComboBox= createMenuDesplegable(new String[]{"Ruleta", "Torneo", "Estocástico", "Truncamiento", "Restos", "Ranking"}));
        //Operadores de cruce
        MyPanel p2 = new MyPanel();
        p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
        p2.add(createLabel("Operadores de cruce"));
        p2.add(crossMethodComboBox= createMenuDesplegable(new String[]{"CO", "CX", "ERX", "Invented", "OX", "OXPP", "PMX"}));
        //Mutación
        MyPanel p3 = new MyPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.X_AXIS));
        p3.add(createLabel("Mutación"));
        p3.add(mutationMethodComboBox = createMenuDesplegable(new String[]{"Heuristic", "Insertion", "Interchange", "Invented", "Inversion"}));

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

        //Seed
        MyPanel p8 = new MyPanel();
        p8.setLayout(new BoxLayout(p8,BoxLayout.X_AXIS));
        p8.add(createLabel("Semilla"));
        p8.add(seedField= createNumericField(3000));

        MyPanel p9 = new MyPanel();
        p9.setLayout(new BoxLayout(p9,BoxLayout.X_AXIS));
        p9.add(createLabel("Tiles con Cámara"));
        p9.add(tiles_of_interest_field= createNumericField(40));

        MyPanel p10 = new MyPanel();
        p10.setLayout(new BoxLayout(p10,BoxLayout.X_AXIS));
        p10.add(createLabel("Número de drones"));
        p10.add(number_of_drones = createNumericField(1));


        //panelConjunto.add(p);
        panelConjunto.add(p1);
        panelConjunto.add(p2);
        panelConjunto.add(p3);
        panelConjunto.add(p4);
        panelConjunto.add(p5);
        panelConjunto.add(p6);
        panelConjunto.add(p7);
        panelConjunto.add(p8);
        panelConjunto.add(p9);
        panelConjunto.add(p10);
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
        t.setMaximumSize(new Dimension(menuDesplegableSizeX + NumericField.x*2, boxSizeY));
        t.setMinimumSize(new Dimension(menuDesplegableSizeX + NumericField.x*2, boxSizeY));
        return t;
    }

    JPanel createGraphicsMenu(){
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());

        MyPanel central_panel = new MyPanel();
        central_panel.setLayout(new BoxLayout(central_panel, BoxLayout.Y_AXIS));
        pan.add(central_panel, BorderLayout.CENTER);

        plot2D = new Plot2DPanel();
        //plot.setSize(100,100);
        plot2D.addLegend("SOUTH");
        plot2D.setVisible(true);
        central_panel.add(plot2D);

        // ADD LOGS
        central_panel.add(logs = new ScrollTextField());
        logs.set_text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        // ADD BUTTOM BUTTONS


        JPanel infoPan = new JPanel();
        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.X_AXIS));

        /*
        JPanel L = new JPanel();
        L.setLayout(new BoxLayout(L, BoxLayout.X_AXIS));
        L.add(createLabel("Presión Selectiva: "));
        L.add(enforcingValue = createLabel("1"));

        JPanel R = new JPanel();
        R.setLayout(new BoxLayout(R, BoxLayout.X_AXIS));
        R.add(createLabel("Mejor Resultado: "));
        R.add(maxValue = createLabel("0"));

        infoPan.add(L); infoPan.add(R);
        */
        pan.add(infoPan, BorderLayout.NORTH);

        JPanel butPan = new JPanel();
        //infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.X_AXIS));
        Button but = new Button("Start");
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneticAlgorithmParameters g = new GeneticAlgorithmParameters();
                g.plot2d = plot2D;
                g.m = mapRepresentation[2-mapsTabs.getSelectedIndex()];
                //
                g.nGen = Integer.parseInt(nGensField.textField.getText());
                g.nIndInGen = Integer.parseInt(nIndInGenField.textField.getText());
                g.crossProbability = Float.parseFloat(crossProbability.textField.getText()) / 100.0f;
                g.mutationprobability = Float.parseFloat(mutationProbability.textField.getText()) / 100.0f;
                //
                g.crossType = crossHash.get(crossMethodComboBox.getSelectedItem().toString());
                g.selectionType = selectionHash.get(selectionTypeComboBox.getSelectedItem().toString());
                g.mutationType = mutationHash.get(mutationMethodComboBox.getSelectedItem().toString());
                g.elite_ratio = elitismBox.isSelected() ? Float.parseFloat(elitismRatio.textField.getText())/100.0f : 0;

                g.n_interest_points = Integer.parseInt(tiles_of_interest_field.textField.getText());
                g.n_drones = Integer.parseInt(number_of_drones.textField.getText());

                g.log = logs;

                INTGeneticAlgorithm algorithm = new INTGeneticAlgorithm(g);
                /*
                float[] Enforcing_n_Max = codeType==0 ?
                        new BINGeneticAlgorithm(g).getMidSelectionEnforcer_n_getMax() :
                        new REALGeneticAlgorithm(g).getMidSelectionEnforcer_n_getMax();
                 */
                //maxValue.setText(""+Enforcing_n_Max[1]);
                //enforcingValue.setText(""+Enforcing_n_Max[0]);

            }
        });
        but.setSize(new Dimension(100,100));

        Button but2 = new Button("Map Gen");
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapStorage.add_map(generateMap(15,15,Long.parseLong(seedField.textField.getText())),
                        2-mapsTabs.getSelectedIndex());
                mapRepresentation[2-mapsTabs.getSelectedIndex()] = createMap(2-mapsTabs.getSelectedIndex());
                mapsTabs.setComponentAt(mapsTabs.getSelectedIndex(),mapRepresentation[2-mapsTabs.getSelectedIndex()]);
//                mapRepresentation[2-mapsTabs.getSelectedIndex()].WipeMap();
//                mapRepresentation[2-mapsTabs.getSelectedIndex()].DrawPoints(
//                    mapRepresentation[2-mapsTabs.getSelectedIndex()].m.getRandomTiles(
//                            Integer.parseInt(tiles_of_interest_field.textField.getText()),
//                            Long.parseLong(seedField.textField.getText())
//                    )
//                );
                revalidate();
                repaint();
            }
        });
        but2.setSize(new Dimension(100,100));

        butPan.add(but);
        butPan.add(but2);
        pan.add(butPan, BorderLayout.SOUTH);

        for(int i = 0; i < mapRepresentation.length; ++i) {
            mapRepresentation[i] = createMap(i);

            mapsTabs.setComponentAt(i,mapRepresentation[i]);
//            mapRepresentation[i].WipeMap();
//            mapRepresentation[i].DrawPoints(
//                    mapRepresentation[i].m.getRandomTiles(
//                            Integer.parseInt(tiles_of_interest_field.textField.getText()),
//                            Long.parseLong(seedField.textField.getText())
//                    )
//            );
        }

        return pan;
    }

}
