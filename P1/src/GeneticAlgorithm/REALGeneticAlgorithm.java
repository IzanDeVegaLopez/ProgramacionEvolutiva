package GeneticAlgorithm;

import codification.codificacion_binaria;
import codification.codificacion_real;
import crossmethods.cruce_aritmetico;
import crossmethods.cruce_blx_alpha;
import crossmethods.cruce_monopunto;
import crossmethods.cruce_uniforme;
//import elitism_methods.elitismo;
import elitism_methods.elitism;
import fitness.FitnessReturnClass;
import fitness.fitnessFunctions;
import mutation_methods.mutacion_a_nivel_de_gen;
import mutation_methods.mutacion_gaussiana;
import mutation_methods.mutacion_inicial;
import selection_methods.*;

import java.awt.*;
import java.util.ArrayList;

public class REALGeneticAlgorithm extends GeneticAlgorithmBase{
    //2 buffers y van alternando
    codificacion_real[][] cod;
    codificacion_real[] elite_elems;
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;

    int n_elites;
    int[][] elite_values;// 0 value, 1 penalty

    public REALGeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        do_first_gen(p);
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
            new mutacion_inicial().mutar(cod[using_cod_n][i]);
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

        n_elites = (int)(p.elite_ratio * p.nIndInGen);
        elite_elems = new codificacion_real[n_elites];
        for(int i = 0; i < n_elites; ++i){
            elite_elems[i] = new codificacion_real(p.m.m.ocupiedTiles.length, p.m.m.ocupiedTiles[0].length, p.m.m.nCamaras);
        }
        elite_values = new int[2][n_elites];

        bestSol = new FitnessReturnClass();
    }
    void do_first_gen(GeneticAlgorithmParameters p){
        int alternate = (using_cod_n + 1) %2;
        //FITNESS - errors
        int[][] results = new int[2][p.nIndInGen];
        FitnessReturnClass[] ft = new FitnessReturnClass[p.nIndInGen];
        long acum = 0;
        int max = 0;
        boolean mapUpdated = false;
        for (int i = 0; i<p.nIndInGen; i++){
            FitnessReturnClass temp = fitnessFunctions.getFloatFitness(p.m.m,cod[using_cod_n][i], p.isPonderado);
            results[0][i] = temp.totalValue;
            results[1][i] = temp.totalNPenalties;
            ft[i] = temp;
            int graphicResult = results[0][i] - (p.m.m.nCamaras-results[1][i]) *p.m.m.penalty;
            acum += graphicResult;
            max = Math.max(graphicResult,max);
            if(max > (bestSol.totalValue-(p.m.m.nCamaras - bestSol.totalNPenalties)*p.m.m.penalty)){
                bestSol = temp;
                mapUpdated = true;
            }
        }
        //get media gen
        int mid = (int)((float)(acum)/(float)(p.nIndInGen));
        //get max gen DONE
        //get max abs DONE

        //ELITISMO------------------------------------------------------------------------------------------------
        //CHOSE FIRST GEN ELITES
        if(n_elites > 0) {
            int[] best = new elitism().choose_elite(n_elites, results[0]);
            for (int i = 0; i < best.length; ++i) {
                elite_elems[i].setAllData(cod[currentGen][best[i]].retrieveAllData());
                elite_values[0][i] = results[0][best[i]];
                elite_values[1][i] = results[1][best[i]];
            }
        }
        //--------------------------------------------------------------------------------------------------------

        if(mapUpdated) p.m.putAllBinCameras(bestSol);

        //PINTAR
        //eliminate all lines
        //paint 3 lines again
        for(int i = 2; i >=0; --i){
            p.plot2d.removePlot(0);
        }

        plotValues[0][currentGen] = mid;
        plotValues[1][currentGen] = max;
        plotValues[2][currentGen] = bestSol.totalValue -(p.m.m.nCamaras- bestSol.totalNPenalties)*p.m.m.penalty;
        p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
        p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
        p.plot2d.addLinePlot("ABSOLUTE BEST",Color.BLUE, plotValues[3], plotValues[2]);
        //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');


        //SELECCIÓN
        int[] select=new int[0];
        switch(p.selectionType){
            case 0:{//RULETA
                ruleta r = new selection_methods.ruleta();
                select = r.chooseEntities(results[0]);
                midSelectionEnforcer += r.t.presion_selectiva;
                break;
            }
            case 1:{//TORNEO
                torneo t = new selection_methods.torneo();
                select = t.chooseEntities(results[0]);
                midSelectionEnforcer += t.t.presion_selectiva;
                break;
            }
            case 2:{//ESTOCASTICO
                estocastico e = new selection_methods.estocastico();
                select = e.chooseEntities(results[0]);
                midSelectionEnforcer+=e.t.presion_selectiva;
                break;
            }
            case 3:{//TRUNCAMIENTO
                truncamiento t = new truncamiento();
                select = t.chooseEntities(results[0]);
                midSelectionEnforcer+=t.t.presion_selectiva;
                break;
            }
            case 4:{//RESTOS
                restos r = new restos();
                select = r.chooseEntities(results[0]);
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
            m.mutar(cod[using_cod_n][i]);
        }
        ++currentGen;
    }
    void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            int alternate = (using_cod_n + 1) %2;
            //FITNESS
            int[][] results = new int[2][p.nIndInGen];
            FitnessReturnClass ft;
            long acum = 0;
            int max = 0;
            boolean mapUpdated = false;
            for (int i = 0; i<p.nIndInGen; i++){
                FitnessReturnClass temp = fitnessFunctions.getFloatFitness(p.m.m,cod[using_cod_n][i], p.isPonderado);
                results[0][i] = temp.totalValue;
                results[1][i] = temp.totalNPenalties;
                int graphicResult = results[0][i] -(p.m.m.nCamaras- temp.totalNPenalties)*p.m.m.penalty;
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
            //ELITISMO
            if(n_elites > 0) {
                //INTRODUCE LAST ELITES
                int[] worst = new elitism().choose_worst(n_elites, results[0]);
                //int worst_total_value = 0;
                for (int i = 0; i < worst.length; ++i) {
                    //worst_total_value -= results[0][worst[i]] - ((p.m.m.nCamaras-results[1][worst[i]]) *p.m.m.penalty);
                    //worst_total_value += elite_values[0][i] - (p.m.m.nCamaras-elite_values[1][i])*p.m.m.penalty;
                    cod[using_cod_n][worst[i]].setAllData(elite_elems[i].retrieveAllData());
                    results[0][worst[i]] = elite_values[0][i];
                    results[1][worst[i]] = elite_values[1][i];
                }
                //worst_total_value *= (float) (n_elites) / (float) (p.nIndInGen);
                int graphicResult = bestSol.totalValue - ((p.m.m.nCamaras- bestSol.totalNPenalties) *p.m.m.penalty);
                //mid += worst_total_value;
                //max = Math.max(max, graphicResult);
                //CHOSE NEW ELITES
                int[] best = new elitism().choose_elite(n_elites, results[0]);
                for (int i = 0; i < best.length; ++i) {
                    elite_elems[i].setAllData(cod[using_cod_n][best[i]].retrieveAllData());
                    elite_values[0][i] = results[0][best[i]];
                    elite_values[1][i] = results[1][best[i]];
                }
            }
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
                    select = r.chooseEntities(results[0]);
                    midSelectionEnforcer += r.t.presion_selectiva;
                    break;
                }
                case 1:{//TORNEO
                    torneo t = new torneo();
                    select = t.chooseEntities(results[0]);
                    midSelectionEnforcer += t.t.presion_selectiva;
                    break;
                }
                case 2:{//ESTOCASTICO
                    estocastico e = new estocastico();
                    select = e.chooseEntities(results[0]);
                    midSelectionEnforcer+=e.t.presion_selectiva;
                    break;
                }
                case 3:{//TRUNCAMIENTO
                    truncamiento t = new truncamiento();
                    select = t.chooseEntities(results[0]);
                    midSelectionEnforcer+=t.t.presion_selectiva;
                    break;
                }
                case 4:{//RESTOS
                    restos r = new restos();
                    select = r.chooseEntities(results[0]);
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
                case 2: {//ARITMETICO
                    cruce_aritmetico crux = new cruce_aritmetico();
                    for (int i = 0; i + 1 < chosenForCross.size(); i += 2) {
                        crux.crossAll(cod[using_cod_n], chosenForCross.get(i), chosenForCross.get(i + 1));
                    }
                    break;
                }
                case 3: {//BLX-ALPHA
                    cruce_blx_alpha crux = new cruce_blx_alpha();
                    for (int i = 0; i + 1 < chosenForCross.size(); i += 2) {
                        crux.crossAll(cod[using_cod_n], chosenForCross.get(i), chosenForCross.get(i + 1));
                    }
                    break;
                }
            }

            //MUTACIÓN
            switch(p.mutationType){
                case 0:{//BIT-LEVEL
                    mutacion_a_nivel_de_gen m = new mutacion_a_nivel_de_gen(p.mutationprobability);
                    for(int i = 0; i < p.nIndInGen; ++i){
                        m.mutar(cod[using_cod_n][i]);
                    }
                    break;
                }
                case 1:{//GAUSSIANA
                    mutacion_gaussiana m = new mutacion_gaussiana(p.mutationprobability);
                    for(int i = 0; i < p.nIndInGen;++i){
                        m.mutar(cod[using_cod_n][i]);
                    }
                }
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
