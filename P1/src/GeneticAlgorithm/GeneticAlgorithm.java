package GeneticAlgorithm;

import Mapas.Map;
import codification.codificacion_binaria;
import org.math.plot.Plot2DPanel;

import java.awt.*;

public class GeneticAlgorithm {
    codificacion_binaria[] cod;
    double[][] plotValues;
    int currentGen = 0;
    public GeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    public void startGeneticAlgorithm(GeneticAlgorithmParameters p){
        currentGen = 0;
        cod = new codificacion_binaria[p.nIndInGen];
        for(int i = 0; i < p.nIndInGen; ++i){
            cod[i] = new codificacion_binaria(p.m.ocupiedTiles.length, p.m.ocupiedTiles[0].length, p.m.nCamaras);
        }
        plotValues = new double[4][p.nGen];

        for(int i = 0; i < p.nGen; ++i){
            plotValues[3][i] = i;
        }

        int i = p.plot2d.addLinePlot("BLUE",Color.BLUE, plotValues[0],plotValues[3]);
        int i2 = p.plot2d.addLinePlot("RED",Color.RED, plotValues[1],plotValues[3]);
        int i3 = p.plot2d.addLinePlot("GREEN",Color.GREEN, plotValues[2],plotValues[3]);
    }
    public void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            //FITNESS
                //get media gen
                //get max gen
                //get max abs
            //PINTAR
                //eliminate all lines
                //paint 3 lines again
            for(int i = 2; i >=0; --i){
                p.plot2d.removePlot(0);
                plotValues[i][currentGen] = currentGen+i;
            }
            p.plot2d.addLinePlot("BLUE",Color.BLUE, plotValues[0],plotValues[3]);
            p.plot2d.addLinePlot("RED",Color.RED, plotValues[1],plotValues[3]);
            p.plot2d.addLinePlot("GREEN",Color.GREEN, plotValues[2],plotValues[3]);

            //SELECCIÓN
            //CRUCE
            //MUTACIÓN
            ++currentGen;
        }
    }
    public void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        //DONT KNOW, WHATEVER
    }
}
