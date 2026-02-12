package GeneticAlgorithm;

import Mapas.Map;
import codification.codificacion_binaria;
import org.math.plot.Plot2DPanel;
import fitness.*;
import selection_methods.*;

import java.awt.*;

public class GeneticAlgorithm {
    codificacion_binaria[] cod;
    double[][] plotValues;
    int currentGen = 0;
    FitnessReturnClass bestSol;
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
        //Cretion plot array
        plotValues = new double[4][p.nGen];
        for(int i = 0; i < p.nGen; ++i){
            plotValues[3][i] = i;
        }
        //erase lines already written
        if(p.plot2d.getPlots().size()==0) {
            p.plot2d.addLinePlot("MID",Color.BLUE, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.GREEN, plotValues[3], plotValues[2]);
        }

        bestSol = new FitnessReturnClass();
    }
    public void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            //FITNESS
            int[] results = new int[p.nIndInGen];
            FitnessReturnClass ft;
            long acum = 0;
            int max = 0;
            for (int i = 0; i<p.nIndInGen; i++){
                FitnessReturnClass temp = fitnessFunctions.getBinFitness(p.m,cod[i]);
                results[i] = temp.totalValue;
                acum += results[i];
                max = Math.max(results[i],max);
                if(max > bestSol.totalValue){
                    bestSol = temp;
                }
            }
            //get media gen
            int mid = (int)acum/p.nIndInGen;
            //get max gen DONE
            //get max abs DONE
            //PINTAR
                //eliminate all lines
                //paint 3 lines again
            for(int i = 2; i >=0; --i){
                p.plot2d.removePlot(0);
                //plotValues[i][currentGen] = currentGen+i;
            }
            plotValues[0][currentGen] = mid;
            plotValues[1][currentGen] = max;
            plotValues[2][currentGen] = bestSol.totalValue;
            p.plot2d.addLinePlot("MID",Color.BLUE, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.GREEN, plotValues[3], plotValues[2]);
            IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');
            //SELECCIÓN
            //for (int t : results) IO.print(Integer.toString(t)+" ");
            //IO.print(("\n"));
            int[] select = new selection_methods.
                    restos().chooseEntities(results);
            //for (int t : select) IO.print(Integer.toString(t)+" ");
            //IO.print(("\n"));
            //CRUCE
            //MUTACIÓN
            ++currentGen;
        }
    }
    public void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        //DONT KNOW, WHATEVER
    }
}
