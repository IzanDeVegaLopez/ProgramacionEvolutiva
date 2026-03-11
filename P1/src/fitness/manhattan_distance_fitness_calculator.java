package fitness;

import Mapas.Map;
import Mapas.pathing.NavA;
import codification.codificacion_entera;
import utils.Vector2;

public class manhattan_distance_fitness_calculator implements base_fitness_calculator{
    float[] dron_multiplier = {1/1.5f, 1, 1/0.7f, 1/1.2f, 2};
    public manhattan_distance_fitness_calculator(Map m){
        reset_already_calculated_costs(m);
    }
    public FitnessReturnClass calculate_fitness(Map m, codificacion_entera[] cod){
        FitnessReturnClass fit = new FitnessReturnClass(cod.length);
        fit.best_value = 1000000;
        for(int i = 1; i < cod.length; ++i){
            //TODO: return true value
            fit.totalValue[i] = calculate_one_codification_fitness(m,cod[i]);
            if(fit.totalValue[i] < fit.best_value){
                fit.best_value = fit.totalValue[i];
            }
            fit.mid += fit.totalValue[i];
        }
        fit.mid /= cod.length;
        return fit;
    }

    public double calculate_one_codification_fitness(Map m, codificacion_entera cod){
        double[] total_fitness = new double[5];
        for(int i = 0; i < total_fitness.length; ++i){
            total_fitness[i] = 0;
        }

        //start in start pos
        Vector2 last_pos = m.interest_points[m.interest_points.length-1];
        int dron = 0;
        for(int i = 0; i < cod.get_size(); ++i){
            //if codification value is greater than the number of points we set the starting point
            int index = Math.min(cod.get_value(i),m.interest_points.length-1);
            total_fitness[dron] += dron_multiplier[dron] * return_new_cost(m,last_pos, m.interest_points[index]);
            if(index==m.interest_points.length)++dron;
            last_pos = m.interest_points[index];
        }
        //end in start pos
        total_fitness[dron] += return_new_cost(m, last_pos, m.interest_points[m.interest_points.length-1]);

        int i = 0;
        double max = 0;
        double min = 10000;
        while(i < 5 && total_fitness[i]>0){
            max = Math.max(max,total_fitness[i]);
            min = Math.min(min,total_fitness[i]);
            ++i;
        }
        return max + (max-min)*0.5f;
    }

    int return_new_cost(Map m, Vector2 last_point, Vector2 next_point){
        int i_index = last_point.x*m.importanceMap.length+ last_point.y;
        int j_index = next_point.x*m.importanceMap.length+next_point.y;
        if(cost_already_calculated[i_index][j_index] != -1) return cost_already_calculated[i_index][j_index];
        return cost_already_calculated[i_index][j_index] = NavA.findPath(m,last_point,next_point).best;
        //TODO: Figure out what to do with saving the path
    }

    static void reset_already_calculated_costs(Map m){
        int total_tiles =m.importanceMap.length*m.importanceMap[0].length;
        cost_already_calculated = new int[total_tiles][total_tiles];
        for (int i = 0; i < cost_already_calculated.length; ++i){
            for(int j = 0; j < cost_already_calculated[0].length; ++j){
                cost_already_calculated[i][j]=-1;
            }
        }
    }

    static int[][] cost_already_calculated;
}
