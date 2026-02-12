package GeneticAlgorithm;

import Mapas.Map;
import codification.codificacion_binaria;
import crossmethods.cruce_monopunto;
import mutation_methods.mutacion_inicial;
import org.math.plot.Plot2DPanel;
import fitness.*;
import selection_methods.*;

import java.awt.*;
import java.util.ArrayList;

public class GeneticAlgorithm {
    //2 buffers y van alternando
    codificacion_binaria[][] cod;
    int using_cod_n = 0;
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
        using_cod_n = 0;
        int alternate = (using_cod_n+1)%2;
        cod = new codificacion_binaria[][]{new codificacion_binaria[p.nIndInGen],new codificacion_binaria[p.nIndInGen]};
        for(int i = 0; i < p.nIndInGen; ++i){
            cod[using_cod_n][i] = new codificacion_binaria(p.m.m.ocupiedTiles.length, p.m.m.ocupiedTiles[0].length, p.m.m.nCamaras);
            cod[alternate][i] = new codificacion_binaria(p.m.m.ocupiedTiles.length, p.m.m.ocupiedTiles[0].length, p.m.m.nCamaras);
            mutacion_inicial.mutar_init(cod[using_cod_n][i]);
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
            boolean mapUpdated = false;
            for (int i = 0; i<p.nIndInGen; i++){
                FitnessReturnClass temp = fitnessFunctions.getBinFitness(p.m.m,cod[using_cod_n][i]);
                results[i] = temp.totalValue;
                acum += results[i];
                max = Math.max(results[i],max);
                if(max > bestSol.totalValue){
                    bestSol = temp;
                    mapUpdated = true;
                }
            }
            if(mapUpdated) p.m.putAllBinCameras(bestSol);
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
            int[] select = new selection_methods.
                    restos().chooseEntities(results);
            int alternate = (using_cod_n + 1) %2;
            for(int i=0;i<select.length;++i){
                cod[alternate][i].setAllData(cod[using_cod_n][select[i]].retrieveAllData());
            }
            using_cod_n = alternate;
            //CRUCE
            //--> param probabilidad de cruce
            ArrayList<Integer> chosenForCross = new ArrayList<Integer>(0);
            for(int i = 0; i < p.nIndInGen; ++i){
                if(Math.random() <= p.crossProbability) chosenForCross.add(i);
            }
            cruce_monopunto crux = new cruce_monopunto();
            for(int i = 0; i + 1 < chosenForCross.size(); i+=2){
                crux.crossAll(cod[using_cod_n], chosenForCross.get(i), chosenForCross.get(i+1));
            }
            //MUTACIÓN
            ++currentGen;
        }
    }
    public void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        //DONT KNOW, WHATEVER
    }
}
