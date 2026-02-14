package GeneticAlgorithm;

import codification.codificacion_binaria;
import crossmethods.cruce_monopunto;
import crossmethods.cruce_uniforme;
import elitism_methods.elitismReturnValue;
//import elitism_methods.elitismo;
import elitism_methods.elitismo_bin;
import mutation_methods.*;
import fitness.*;
import selection_methods.*;

import java.awt.*;
import java.util.ArrayList;

public class BINGeneticAlgorithm extends GeneticAlgorithmBase {
    //2 buffers y van alternando
    elitismo_bin elit_bin;
    codificacion_binaria[][] cod;
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;
//    int[] last_elite;
//    int[] last_elite_values;
    public BINGeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    void startGeneticAlgorithm(GeneticAlgorithmParameters p){
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
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.BLUE, plotValues[3], plotValues[2]);
        }
//        last_elite = new int[(int)Math.floor(p.elite_ratio / p.nGen)];
//        last_elite_values = new int[last_elite.length];

        elit_bin = new elitismo_bin((int) (p.elite_ratio * p.nGen));
        bestSol = new FitnessReturnClass();
    }
    void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            int alternate = (using_cod_n + 1) %2;
            //FITNESS
            int[] results = new int[p.nIndInGen];
            FitnessReturnClass ft;
            long acum = 0;
            int max = 0;
            boolean mapUpdated = false;
            for (int i = 0; i<p.nIndInGen; i++){
                FitnessReturnClass temp = fitnessFunctions.getBinFitness(p.m.m,cod[using_cod_n][i], p.isPonderado);
                temp.totalValue -=(p.m.m.nCamaras- temp.totalNPenalties)*p.m.m.penalty;
                results[i] = temp.totalValue;
                acum += results[i];
                max = Math.max(results[i],max);
                if(max > bestSol.totalValue){
                    bestSol = temp;
                    mapUpdated = true;
                }
            }
            //get media gen
            int mid = (int)acum/p.nIndInGen;
            //get max gen DONE
            //get max abs DONE

            //ELITISMO
            int[] elit_results = elit_bin.introduce_elite_bin(results, cod[using_cod_n]);
            mid += (elit_results[0] - elit_results[1]) / p.nIndInGen;
            for (int i = 0; i<p.nIndInGen; i++){
                max = Math.max(results[i],max);
                if(max > bestSol.totalValue){
                    bestSol = new FitnessReturnClass();
                    bestSol.totalValue = results[i];
                    mapUpdated = true;
                }
            }
            elit_bin.extract_elite_bin(results, cod[using_cod_n]);
            if(mapUpdated) p.m.putAllBinCameras(bestSol);

            //PINTAR
                //eliminate all lines
                //paint 3 lines again
            for(int i = 2; i >=0; --i){
                p.plot2d.removePlot(0);
                //plotValues[i][currentGen] = currentGen+i;
            }
            /*
            int idxAuxToGetMax=0;
            for(int i = 0; i < eliteIdx.maxSelected.length; ++i){
                //Set the minimum element of this gen to the max of the last one
                cod[using_cod_n][eliteIdx.minSelected[i]].setAllData(cod[alternate][last_elite[i]].retrieveAllData());
                while(idxAuxToGetMax < last_elite.length && last_elite_values[i] > results[idxAuxToGetMax]){
                    ++idxAuxToGetMax;
                }
                //Its a new max
                if(idxAuxToGetMax < last_elite.length){

                }
                //The max in i pos is still the max
                if(i < idxAuxToGetMax){

                }
                //Check if new greater is greater than last time, if it is the element we just reinserted we should keep it instead
                //last_elite[i]=eliteIdx.maxSelected[i];
                //last_elite_values[i]=
            }*/

            plotValues[0][currentGen] = mid;
            plotValues[1][currentGen] = max;
            plotValues[2][currentGen] = bestSol.totalValue;
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.BLUE, plotValues[3], plotValues[2]);
            //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');


            //SELECCIÓN
            int[] select=new int[0];
            switch(p.selectionType){
                case 0:{//RULETA
                    ruleta r = new selection_methods.ruleta();
                    select = r.chooseEntities(results);
                    midSelectionEnforcer += r.t.presion_selectiva;
                    break;
                }
                case 1:{//TORNEO
                    torneo t = new selection_methods.torneo();
                    select = t.chooseEntities(results);
                    midSelectionEnforcer += t.t.presion_selectiva;
                    break;
                }
                case 2:{//ESTOCASTICO
                    estocastico e = new selection_methods.estocastico();
                    select = e.chooseEntities(results);
                    midSelectionEnforcer+=e.t.presion_selectiva;
                    break;
                }
                case 3:{//TRUNCAMIENTO
                    truncamiento t = new truncamiento();
                    select = t.chooseEntities(results);
                    midSelectionEnforcer+=t.t.presion_selectiva;
                    break;
                }
                case 4:{//RESTOS
                    restos r = new restos();
                    select = r.chooseEntities(results);
                    midSelectionEnforcer+=r.t.presion_selectiva;
                    break;
                }
            }
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
            switch(p.crossType) {
                case 0: { //MONOPUNTO
                    cruce_monopunto crux = new cruce_monopunto();
                    for (int i = 0; i + 1 < chosenForCross.size(); i += 2) {
                        crux.crossAll(cod[using_cod_n], chosenForCross.get(i), chosenForCross.get(i + 1));
                    }
                    break;
                }
                case 1: {//UNIFORME
                    cruce_uniforme crux = new cruce_uniforme();
                    for (int i = 0; i + 1 < chosenForCross.size(); i += 2) {
                        crux.crossAll(cod[using_cod_n], chosenForCross.get(i), chosenForCross.get(i + 1));
                    }
                    break;
                }
            }

            //MUTACIÓN
            mutacion m = new mutacion(p.mutationprobability);
            for(int i = 0; i < p.nIndInGen; ++i){
                mutacion.mutar(cod[using_cod_n][i]);
            }
            ++currentGen;
        }
    }
    void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        midSelectionEnforcer /= p.nGen;
    }
}
