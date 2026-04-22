package GeneticAlgorithm;

//import elitism_methods.elitismo;
import Mapas.mapStorage;
import codification.Generation;
import codification.IndividualCodification;
import codification.RoverExecutionContext;
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
    IndividualCodification best;
    double[] elite_values;// 0 value, 1 penalty
    int using_cod_n = 0;
    double[][] plotValues;
    int currentGen = 0;
    int n_elites;
    double best_sol_yet = Double.NEGATIVE_INFINITY;
    selection_method select_method;
    BaseMutation mutation_method;
    //manhattan_distance_fitness_calculator fit_calculator;
    cross_method crux;
    double presion_selectiva_suma;

    RoverExecutionContext ctx = new RoverExecutionContext();

    elitism elt;
    public TreeGeneticAlgorithm(GeneticAlgorithmParameters p) throws Exception{
        startGeneticAlgorithm(p);
        do_first_gen(p);
        while(currentGen < p.nGen) {
            loopGeneticAlgorithm(p);
        }
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
        gen[0] = new Generation(p.nIndInGen, p.max_depth, Generation.randomization.RANDOMIZE);
        gen[1] = new Generation(p.nIndInGen);
        best = new IndividualCodification();
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
        choose_cross_method();

        initialize_codification(p);

        initialize_plot(p);

        initialize_elites(p);


    }

    void do_first_gen(GeneticAlgorithmParameters p) throws Exception{
        int alternate = (using_cod_n + 1) %2;

        FitnessReturnType ft = FitnessCalculator.calculate_fitness(p.maps, gen[using_cod_n], ctx, p.bloating_coef);
        if(ft.best_value > best_sol_yet){
            best_sol_yet = ft.best_value;
            best.impersonate(gen[using_cod_n].get(ft.best_value_idx));
            System.out.print("\n Best value chosen Yet => "+ best_sol_yet + " in Gen "+ currentGen+"\n");
            //endGeneticAlgorithm(p);
        }

        //ELITISMO------------------------------------------------------------------------------------------------
        //CHOSE FIRST GEN ELITES
        int[] elites_chosen = new elitism().choose_elite(n_elites, ft.fit);
        for (int i = 0; i < elites_chosen.length; ++i) {
            elite_elems.copy_individual(i,gen[using_cod_n].get(elites_chosen[i]));
            elite_values[i] = ft.fit[elites_chosen[i]];
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
        //System.out.print("Gen 0: "+ft.mid+" "+best_sol_yet+" "+ ft.best_value+'\n');

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
            if(Math.random() < p.mutationprobability) mutation_method.mutate(gen[using_cod_n].get(i));
        }

    ++currentGen;
    }

    void loopGeneticAlgorithm(GeneticAlgorithmParameters p) throws Exception{
        mapStorage.reset_maps();
        int alternate = (using_cod_n + 1) %2;

        FitnessReturnType ft = FitnessCalculator.calculate_fitness(p.maps, gen[using_cod_n], ctx, p.bloating_coef);
        if(ft.best_value > best_sol_yet){
            best_sol_yet = ft.best_value;
            System.out.print("\n Best value chosen Yet => "+ best_sol_yet + " in Gen "+ currentGen+"\n");
            best.impersonate(gen[using_cod_n].get(ft.best_value_idx));
            //endGeneticAlgorithm(p);
        }

        //ELITISMO------------------------------------------------------------------------------------------------
        if(n_elites > 0) {
            //INTRODUCE LAST ELITES
            int[] worst = new elitism().choose_worst(n_elites, ft.fit);
            for (int i = 0; i < worst.length; ++i) {
                gen[using_cod_n].copy_individual(worst[i], elite_elems.get(i));
                ft.fit[worst[i]] = elite_values[i];
            }
            ft.best_value = best_sol_yet;
            //CHOSE NEW ELITES
            int[] elites_chosen = new elitism().choose_elite(n_elites, ft.fit);
            for (int i = 0; i < elites_chosen.length; ++i) {
                elite_elems.copy_individual(i, gen[using_cod_n].get(elites_chosen[i]));
                elite_values[i] = ft.fit[elites_chosen[i]];
            }
        }
        //--------------------------------------------------------------------------------------------------------

        //SELECCIÓN
        int[] select = select_method.chooseEntities(ft.fit);
        //Copy the selected entities into the next generation slot
        for(int i=0;i<select.length;++i){
            gen[alternate].copy_individual(i, gen[using_cod_n].get(select[i]));
        }

        presion_selectiva_suma += select_method.get_selection_enforcer();

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
        //System.out.print("Gen "+currentGen+": "+ft.mid+" "+best_sol_yet+" "+ ft.best_value+'\n');
        //IO.print(mid+" "+max+" "+ bestSol.totalValue+'\n');

        //PAINT IF NEEDED
        /*
        if(mapUpdated) {
            p.m.WipeMapBackground();
            p.m.DrawPaths(ft.path_of_best);
            p.log.clear_text();
        }*/

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
            if(Math.random() < p.mutationprobability) mutation_method.mutate(gen[using_cod_n].get(i));
        }

        ++currentGen;
    }
    void endGeneticAlgorithm(GeneticAlgorithmParameters p) throws Exception{
        p.log.set_text(best.node_tree.write_me_down(0));
        for(int i = 0; i<p.maps.length; ++i) {
            var fit_ret = FitnessCalculator.calculate_fitness_given_map_get_tiles(p.maps[i].m, best, ctx);
            p.maps[i].recolor_map(fit_ret.all_tiles_reached,fit_ret.rrt.final_tile);
            p.log.add_text("\nMAP "+(i+1)+":\n" +
                    "\nColisiones: "+fit_ret.rrt.colisiones +
                            "\nArena: "+fit_ret.rrt.arena +
                            "\nRecompensa Visual: "+fit_ret.rrt.recompensa_visual +
                            "\nCasillas Exploradas: "+fit_ret.rrt.casillas_exploradas +
                            "\nMuestras Recogidas: "+fit_ret.rrt.muestras_recogidas
            );
            p.log.add_text("\nResultado Final: "+ FitnessCalculator.calculate_fitness_given_results(fit_ret.rrt), Color.BLUE);
        }
        p.log.add_text("\n\nMejor Resultado: " + best_sol_yet, Color.RED);

    }
}
