package GeneticAlgorithm;

import codification.codificacion_real;
import crossmethods.cruce_monopunto;
import crossmethods.cruce_uniforme;
//import elitism_methods.elitismo;
import fitness.FitnessReturnClass;
import fitness.fitnessFunctions;
import mutation_methods.mutacion_a_nivel_de_gen;
import mutation_methods.mutacion_inicial;
import selection_methods.*;

import java.awt.*;
import java.util.ArrayList;

public class REALGeneticAlgorithm extends GeneticAlgorithmBase{
    //2 buffers y van alternando
    codificacion_real[][] cod;
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;

    int[] last_elite;
    int[] last_elite_values;
    public REALGeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    void startGeneticAlgorithm(GeneticAlgorithmParameters p){
        currentGen = 0;
        using_cod_n = 0;
        int alternate = (using_cod_n+1)%2;
        cod = new codificacion_real[][]{new codificacion_real[p.nIndInGen],new codificacion_real[p.nIndInGen]};
        for(int i = 0; i < p.nIndInGen; ++i){
            cod[using_cod_n][i] = new codificacion_real(p.m.m.nCamaras, p.m.m.ocupiedTiles.length, p.m.m.ocupiedTiles[0].length);
            cod[alternate][i] = new codificacion_real(p.m.m.nCamaras,p.m.m.ocupiedTiles.length, p.m.m.ocupiedTiles[0].length);
            mutacion_inicial.mutar(cod[using_cod_n][i]);
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
        last_elite = new int[(int)Math.floor(p.elite_ratio / p.nGen)];
        last_elite_values = new int[last_elite.length];

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
                FitnessReturnClass temp = fitnessFunctions.getFloatFitness(p.m.m,cod[using_cod_n][i], p.isPonderado);
                results[i] = temp.totalValue;
                int graphicResult = results[i] -(p.m.m.nCamaras- temp.totalNPenalties)*p.m.m.penalty;
                acum += graphicResult;
                max = Math.max(graphicResult,max);
                if(max > (bestSol.totalValue-(p.m.m.nCamaras- bestSol.totalNPenalties)*p.m.m.penalty)){
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
            }

            plotValues[0][currentGen] = mid;
            plotValues[1][currentGen] = max;
            plotValues[2][currentGen] = bestSol.totalValue -(p.m.m.nCamaras- bestSol.totalNPenalties)*p.m.m.penalty;;
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.BLUE, plotValues[3], plotValues[2]);
            //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');


            //SELECCIÓN
            int[] select=new int[0];
            switch(p.selectionType){
                case 0:{//RULETA
                    ruleta r = new ruleta();
                    select = r.chooseEntities(results);
                    midSelectionEnforcer += r.t.presion_selectiva;
                    break;
                }
                case 1:{//TORNEO
                    torneo t = new torneo();
                    select = t.chooseEntities(results);
                    midSelectionEnforcer += t.t.presion_selectiva;
                    break;
                }
                case 2:{//ESTOCASTICO
                    estocastico e = new estocastico();
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
            mutacion_a_nivel_de_gen m = new mutacion_a_nivel_de_gen(p.mutationprobability);
            for(int i = 0; i < p.nIndInGen; ++i){
                mutacion_a_nivel_de_gen.mutar(cod[using_cod_n][i]);
            }
            ++currentGen;
        }

    }
    void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        midSelectionEnforcer /= p.nGen;
        bestSol.totalValue-=(p.m.m.nCamaras- bestSol.totalNPenalties)*p.m.m.penalty;
    }
    public float[] getMidSelectionEnforcer_n_getMax(){
        return new float[]{midSelectionEnforcer, bestSol.totalValue};
    }
}
