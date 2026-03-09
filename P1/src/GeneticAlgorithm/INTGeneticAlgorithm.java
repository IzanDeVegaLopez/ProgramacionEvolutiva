package GeneticAlgorithm;

//import elitism_methods.elitismo;
import codification.codificacion_entera;
import crossmethods.*;
import elitism_methods.*;
//import mutation_methods.*;
import fitness.*;
import mutation_methods.*;
import selection_methods.*;

import java.awt.*;
import java.util.ArrayList;

public class INTGeneticAlgorithm extends GeneticAlgorithmBase {
    //2 buffers y van alternando
    codificacion_entera[][] cod;
    codificacion_entera[] elite_elems;
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;
    int n_elites;
    int best_sol_yet = -1;
    selection_method select_method;
    base_fitness_calculator fit_calculator;
    base_cross_method crux;
    mutation_base mut;
    int[][] elite_values;// 0 value, 1 penalty
    //    int[] last_elite;
//    int[] last_elite_values;
    public INTGeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        do_first_gen(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    void startGeneticAlgorithm(GeneticAlgorithmParameters p){
        currentGen = 0;
        using_cod_n = 0;
        int alternate = (using_cod_n+1)%2;
        fit_calculator = new manhattan_distance_fitness_calculator();
        cod = new codificacion_entera[][]{new codificacion_entera[p.nIndInGen],new codificacion_entera[p.nIndInGen]};
        int n_elems_total = p.n_drones + p.n_interest_points;
        for(int i = 0; i < p.nIndInGen; ++i){
            cod[using_cod_n][i] = new codificacion_entera(n_elems_total);
            cod[using_cod_n][i].initialize_with_stair_shape();

            cod[alternate][i] = new codificacion_entera(n_elems_total);
            cod[alternate][i].initialize_with_stair_shape();

            //TODO: mutación inicial
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
        elite_elems = new codificacion_entera[n_elites];
        for(int i = 0; i < n_elites; ++i){
            elite_elems[i] = new codificacion_entera(n_elems_total);
            elite_elems[i].initialize_with_stair_shape();
        }
        elite_values = new int[2][n_elites];

        switch(p.selectionType){
            case 0:{//RULETA
                select_method = new selection_methods.ruleta();
                break;
            }
            case 1:{//TORNEO
                select_method = new selection_methods.torneo();
                break;
            }
            case 2:{//ESTOCASTICO
                select_method = new selection_methods.estocastico();
                break;
            }
            case 3:{//TRUNCAMIENTO
                select_method = new truncamiento();
                break;
            }
            case 4:{//RESTOS
                select_method = new restos();
                break;
            }
        }

        switch(p.crossType) {
            case 0: { //CO_Cross
                crux = new co_cross();
                break;
            }
            case 1: {//CX_Cross
                crux = new cx_cross();
                break;
            }
            case 2: {//ERX_Cross
                crux = new erx_cross();
                break;
            }
            case 3: {//Invented Cross
                crux = new invented_cross();
                break;
            }
            case 4: {//OX_Cross
                crux = new ox_cross();
                break;
            }
            case 5: {//OXPP_Cross
                crux = new oxpp_cross();
                break;
            }
            case 6: {//PMX_Cross
                crux = new pmx_cross();
                break;
            }
        }
        switch(p.mutationType) {
            case 0: { //heuristic
                mut = new heuristic_mutation();
                break;
            }
            case 1: {//insertion
                mut = new insertion_mutation();
                break;
            }
            case 2: {//interchange
                mut = new interchange_mutation();
                break;
            }
            case 3: {//Invented Cross
                mut = new invented_mutation();
                break;
            }
            case 4: {//OX_Cross
                mut = new inversion_mutation();
                break;
            }
        }
    }

    void do_first_gen(GeneticAlgorithmParameters p){
/*        int alternate = (using_cod_n + 1) %2;
        //FITNESS - errors
        int[] results = new int[p.nIndInGen];
        FitnessReturnClass[] ft = new FitnessReturnClass[p.nIndInGen];
        long acum = 0;
        int max = 0;
        boolean mapUpdated = false;
        for (int i = 0; i<p.nIndInGen; i++){
            FitnessReturnClass temp = fitnessFunctions.getBinFitness(p.m.m,cod[using_cod_n][i], p.isPonderado);
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
        int mid = (int)acum/p.nIndInGen;
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

 */
    }

    void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            int alternate = (using_cod_n + 1) %2;
            //FITNESS
            int[] results = new int[p.nIndInGen];
            long acum = 0;
            int max = 0;
            boolean mapUpdated = false;

            FitnessReturnClass ft = fit_calculator.calculate_fitness(cod[using_cod_n]);
            if(ft.best_value < best_sol_yet){
                best_sol_yet = ft.best_value;
            }

            //ELITISMO------------------------------------------------------------------------------------------------
            /* TODO: Rehacer elitismo
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
                max = Math.max(max, graphicResult);
                //CHOSE NEW ELITES
                int[] best = new elitism().choose_elite(n_elites, results[0]);
                for (int i = 0; i < best.length; ++i) {
                    elite_elems[i].setAllData(cod[using_cod_n][best[i]].retrieveAllData());
                    elite_values[0][i] = results[0][best[i]];
                    elite_values[1][i] = results[1][best[i]];
                }
            }
            */
            //--------------------------------------------------------------------------------------------------------

            //TODO: repaint map
            if(mapUpdated) {}//p.m.putAllBinCameras(bestSol);

            //PINTAR
            //eliminate all lines
            //paint 3 lines again
            for(int i = 2; i >=0; --i){
                p.plot2d.removePlot(0);
            }

            plotValues[0][currentGen] = ft.mid;
            plotValues[1][currentGen] = ft.best_value;
            plotValues[2][currentGen] = best_sol_yet;
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUTE BEST",Color.BLUE, plotValues[3], plotValues[2]);
            //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');


            //SELECCIÓN
            int[] select=new int[0];
            select_method.chooseEntities(ft.totalValue);
            //Copy the selected entities into the next generation slot
            for(int i=0;i<select.length;++i){
                cod[alternate][i].copy(cod[using_cod_n][select[i]]);
            }
            using_cod_n = alternate;

            //CRUCE
            //--> param probabilidad de cruce
            ArrayList<Integer> chosenForCross = new ArrayList<Integer>(0);
            for(int i = 0; i < p.nIndInGen; ++i){
                if(Math.random() <= p.crossProbability) chosenForCross.add(i);
            }
            int total_number_of_crosses = chosenForCross.size();
            for(int i = 1; i < total_number_of_crosses; i = i+2){
                crux.cruzar(cod[using_cod_n][chosenForCross.get(i)], cod[using_cod_n][chosenForCross.get(i-1)]);
            }

            //MUTACIÓN
            for(int i = 0; i < p.nIndInGen; ++i){
                if(Math.random() < p.mutationprobability) mut.mutate(cod[using_cod_n][i]);
            }
            ++currentGen;
        }
    }
    void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        midSelectionEnforcer /= p.nGen;
        //bestSol.totalValue-=(p.m.m.nCamaras- bestSol.totalNPenalties)*p.m.m.penalty;
    }
}
