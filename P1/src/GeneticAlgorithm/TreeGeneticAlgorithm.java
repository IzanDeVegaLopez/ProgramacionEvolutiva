package GeneticAlgorithm;

//import elitism_methods.elitismo;
import codification.Generation;
import mutation_methods.*;
import elitism_methods.elitism;
import selection_methods.*;
import crossmethods.*;
import Fitness.*;

import java.awt.*;
import java.util.ArrayList;

public class TreeGeneticAlgorithm {
    //2 buffers y van alternando
    //codificacion_entera[][] cod;
    //codificacion_entera[] elite_elems;
    Generation[] gen = new Generation[2];
    Generation elite_elems;
    double[] elite_values;// 0 value, 1 penalty
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;
    int n_elites;
    double best_sol_yet = Double.POSITIVE_INFINITY;
    selection_method select_method;
    BaseMutation mutation_method;
    //manhattan_distance_fitness_calculator fit_calculator;
    cross_method crux;
    BaseMutation mut;
    double presion_selectiva_suma;

    elitism elt;
    public TreeGeneticAlgorithm(GeneticAlgorithmParameters p) throws Exception{
        startGeneticAlgorithm(p);
        do_first_gen(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    public void choose_mutation_method(int i){
        switch(i) {
            case 0:
                mutation_method = new mutation_methods.funcional();
                break;
            case 1:
                mutation_method = new mutation_methods.hoist();
                break;
            case 2:
                mutation_method = new mutation_methods.random();
                break;
            case 3:
                mutation_method = new mutation_methods.subarbol();
                break;
            case 4:
                mutation_method = new mutation_methods.terminal();
                break;
        }
    }
    public void choose_selection_method(int i){
        switch(i){
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
            case 5:{//RANKING
                select_method = new ranking();
                break;
            }
        }
    }
    public void choose_cross_method(){
        crux = new basic_tree_cross_method();
    }
    public void initialize_plot(GeneticAlgorithmParameters p){
        //Cretion plot array
        plotValues = new double[4][p.nGen];
        for(int i = 0; i < p.nGen; ++i){
            plotValues[3][i] = i;
        }
        //erase lines already written
        if(p.plot2d.getPlots().isEmpty()) {
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUT BEST",Color.BLUE, plotValues[3], plotValues[2]);
        }
    }
    void initialize_codification(GeneticAlgorithmParameters p) throws Exception{
        using_cod_n = 0;
        int alternate = (using_cod_n+1)%2;
        gen[0] = new Generation(p.nIndInGen, Generation.randomization.RANDOMIZE);
        gen[1] = new Generation(p.nIndInGen);
    }

    void initialize_elites(GeneticAlgorithmParameters p){
        n_elites = (int)(p.elite_ratio * p.nIndInGen);

        elite_elems = new Generation(n_elites);

        elite_values = new double[n_elites];

        elt = new elitism();
    }
    void startGeneticAlgorithm(GeneticAlgorithmParameters p) throws Exception{
        currentGen = 0;
        using_cod_n = 0;

        //fit_calculator = new manhattan_distance_fitness_calculator(p.m.m);

        choose_selection_method(p.selectionType);
        choose_mutation_method(p.mutationType);

        initialize_codification(p);

        initialize_plot(p);

        initialize_elites(p);
    }

    void do_first_gen(GeneticAlgorithmParameters p) throws Exception{
        int alternate = (using_cod_n + 1) %2;
        //FITNESS
        boolean mapUpdated = false;

        FitnessReturnType ft = FitnessCalculator.calculate_fitness(gen[using_cod_n]);
        if(ft.best_value < best_sol_yet){
            best_sol_yet = ft.best_value;
            mapUpdated = true;
        }

        //ELITISMO------------------------------------------------------------------------------------------------
        //CHOSE FIRST GEN ELITES
        int[] best = new elitism().choose_elite(n_elites, ft.fit);
        for (int i = 0; i < best.length; ++i) {
            elite_elems.copy_individual(i,gen[using_cod_n].get(best[i]));
            elite_values[i] = ft.fit[best[i]];
        }
        //--------------------------------------------------------------------------------------------------------

        //SELECCIÓN
        int[] select = select_method.chooseEntities(ft.fit);
        //Copy the selected entities into the next generation slot
        for(int i=0;i<select.length;++i){
            gen[alternate].copy_individual(i, gen[using_cod_n].get(select[i]));
            //cod[alternate][i].copy(cod[using_cod_n][select[i]]);
        }


        presion_selectiva_suma += select_method.get_selection_enforcer();
        //IO.println(select_method.get_selection_enforcer());

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

        //PAINT IF NEEDED
        if(mapUpdated) {
            p.m.WipeMapBackground();
            p.m.DrawPaths(ft.path_of_best);
            p.log.clear_text();

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
            crux.cross(gen[using_cod_n].get(chosenForCross.get(i)), gen[using_cod_n].get(chosenForCross.get(i-1)));
        }

        //MUTACIÓN
        for(int i = 0; i < p.nIndInGen; ++i){
            if(Math.random() < p.mutationprobability) mut.mutate(gen[using_cod_n].get(i));
        }

    ++currentGen;
    }

    void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            int alternate = (using_cod_n + 1) %2;
            //FITNESS
            boolean mapUpdated = false;
/*
            FitnessReturnClass ft = fit_calculator.calculate_fitness(cod[using_cod_n]);
            if(ft.best_fitness_result.value < best_sol_yet){
                best_sol_yet = ft.best_fitness_result.value;
                mapUpdated = true;
            }

            //ELITISMO------------------------------------------------------------------------------------------------
            if(n_elites > 0) {
                //INTRODUCE LAST ELITES
                int[] worst = new elitism().choose_worst(n_elites, ft.totalValue);
                //int worst_total_value = 0;
                for (int i = 0; i < worst.length; ++i) {
                    //worst_total_value -= results[0][worst[i]] - ((p.m.m.nCamaras-results[1][worst[i]]) *p.m.m.penalty);
                    //worst_total_value += elite_values[0][i] - (p.m.m.nCamaras-elite_values[1][i])*p.m.m.penalty;
                    cod[using_cod_n][worst[i]].copy(elite_elems[i]);
                    ft.totalValue[worst[i]] = elite_values[i];
                }
                //worst_total_value *= (float) (n_elites) / (float) (p.nIndInGen);
                //int graphicResult = best_sol_yet - ((p.m.m.nCamaras- bestSol.totalNPenalties) *p.m.m.penalty);
                //mid += worst_total_value;
                ft.best_fitness_result.value = best_sol_yet;
                //CHOSE NEW ELITES
                int[] best = new elitism().choose_elite(n_elites, ft.totalValue);
                for (int i = 0; i < best.length; ++i) {
                    elite_elems[i].copy(cod[using_cod_n][best[i]]);
                    elite_values[i] = ft.totalValue[best[i]];
                }
            }
            //--------------------------------------------------------------------------------------------------------

            //SELECCIÓN
            int[] select = select_method.chooseEntities(ft.totalValue);
            //Copy the selected entities into the next generation slot
            for(int i=0;i<select.length;++i){
                cod[alternate][i].copy(cod[using_cod_n][select[i]]);
            }

            presion_selectiva_suma += select_method.get_selection_enforcer();
            //IO.println(select_method.get_selection_enforcer());


            //PINTAR
            //eliminate all lines
            //paint 3 lines again
            for(int i = 2; i >=0; --i){
                p.plot2d.removePlot(0);
            }

            plotValues[0][currentGen] = ft.mid;
            plotValues[1][currentGen] = ft.best_fitness_result.value;
            plotValues[2][currentGen] = best_sol_yet;
            p.plot2d.addLinePlot("MID",Color.GREEN, plotValues[3],plotValues[0]);
            p.plot2d.addLinePlot("BEST IN GEN" ,Color.RED, plotValues[3], plotValues[1]);
            p.plot2d.addLinePlot("ABSOLUTE BEST",Color.BLUE, plotValues[3], plotValues[2]);
            //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');

            if(mapUpdated) {
                p.m.WipeMapBackground();
                p.m.DrawPaths(ft.best_fitness_result.path);
                p.log.clear_text();

                p.log.add_text("Mejor resultado: " + best_sol_yet + "s.\n", Color.BLUE);
                p.log.add_text("Tiempo por drón: ", Color.BLUE);
                for(int i = 0; i < p.n_drones; ++i){
                    p.log.add_text(Double.toString(ft.best_fitness_result.route_duration_per_drone[i])+"s, ", mapReader.PathColors[i]);
                }
                p.log.add_text("\n");
                p.log.add_text("Presión selectiva: " + presion_selectiva_suma/currentGen + "\n", Color.RED);
                int dron = 0;
                int size = cod[using_cod_n][ft.best_codification_index].get_size();
                p.log.add_text("Dron "+(dron+1)+" (x"+manhattan_distance_fitness_calculator.dron_multiplier[dron]+"): ", mapReader.PathColors[dron]);
                for(int i = 0; i < size; ++i){
                    int value = cod[using_cod_n][ft.best_codification_index].get_value(i);
                    p.log.add_text(value+" ", mapReader.PathColors[dron]);
                    //IO.print(value+" ");
                    if(value >= p.n_interest_points){
                        ++dron;
                        p.log.add_text("\nDron "+(dron+1)+" (x"+manhattan_distance_fitness_calculator.dron_multiplier[dron]+"): ", mapReader.PathColors[dron]);
                    }
                }
                //IO.print("\n");
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
            */
            ++currentGen;
        }
    }
    void endGeneticAlgorithm(GeneticAlgorithmParameters p){
    }
}
