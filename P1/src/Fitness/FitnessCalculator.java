package Fitness;

import Graphic.MapRepresentation;
import Mapas.Map;
import codification.Generation;
import codification.IndividualCodification;
import Error.UnreachableCode;
import codification.RoverExecutionContext;
import utils.Vector2;

import java.util.Vector;

public class FitnessCalculator {
    public static FitnessReturnType calculate_fitness(MapRepresentation[] m, Generation gen, RoverExecutionContext ctx, double bloating_coef) throws Exception {
        FitnessReturnType ret = new FitnessReturnType();
        ret.best_value = Double.NEGATIVE_INFINITY;
        ret.fit = new double[gen.all_individuals.length];
        int i = 0;
        for (IndividualCodification cod : gen.all_individuals) {
            if (cod.node_tree == null)
                throw new UnreachableCode("The codification with index " + i + " has a null first tree node");
            ret.fit[i] = calulate_one_individual_fitness(m, cod, ctx,bloating_coef);
            ret.mid += ret.fit[i];
            if (ret.fit[i] > ret.best_value) {
                ret.best_value = ret.fit[i];
                ret.best_value_idx = i;
            }
            ++i;
        }
        ret.mid /= gen.all_individuals.length;
        return ret;
    }

    public static double calulate_one_individual_fitness(MapRepresentation[] m, IndividualCodification cod, RoverExecutionContext ctx, double bloating_coef) throws Exception {
        double total_fitness = 0;
        for (MapRepresentation _m : m) {
            total_fitness += calculate_fitness_given_map(_m.m, cod, ctx);
        }
        int n_nodes = cod.get_number_of_child_nodes();
        return (total_fitness / m.length) - (n_nodes * bloating_coef);
    }

    public static double calculate_fitness_given_map(Map m, IndividualCodification cod, RoverExecutionContext ctx) throws Exception {
        return calculate_fitness_given_results(ctx.do_simulation(m,cod));
    }

    public static RoverExecutionContext.RecorridoReturnTypeWithTilesReached calculate_fitness_given_map_get_tiles(Map m, IndividualCodification cod, RoverExecutionContext ctx) throws Exception {
        RoverExecutionContext.RecorridoReturnTypeWithTilesReached ret = ctx.do_simulation(m, cod, RoverExecutionContext.with_tiles.WITH_TILES);
        return ret;
    }

    public static double calculate_fitness_given_results(RoverExecutionContext.RecorridoReturnType ret)    {
            return 500*ret.muestras_recogidas +
            20*ret.casillas_exploradas +
            2*ret.recompensa_visual -
            30*ret.arena -
            10*ret.colisiones;
    }
}
