package fitness;

import Mapas.Map;
import Mapas.pathing.NavA;
import Mapas.pathing.navA_return_type;
import codification.codificacion_entera;
import utils.Vector2;

import java.util.Vector;

public class manhattan_distance_fitness_calculator implements base_fitness_calculator{
    float[] dron_multiplier = {1/1.5f, 1, 1/0.7f, 1/1.2f, 2};
    Map m;
    public manhattan_distance_fitness_calculator(Map _m){
        m = _m;
        reset_already_calculated_costs();
    }
    public FitnessReturnClass calculate_fitness(codificacion_entera[] cod){
        FitnessReturnClass fit = new FitnessReturnClass(cod.length);
        fit.best_fitness_result.value = 1000000;
        for(int i = 0; i < cod.length; ++i){
            //TODO: return true value
            fitness_return_type fitfit = calculate_one_codification_fitness(cod[i]);
            fit.totalValue[i] = fitfit.value;
            if(fit.totalValue[i] < fit.best_fitness_result.value){
                fit.best_fitness_result = fitfit;
                fit.best_codification_index = i;
            }
            fit.mid += fit.totalValue[i];
        }
        fit.mid /= cod.length;
        return fit;
    }

    public fitness_return_type calculate_one_codification_fitness(codificacion_entera cod){
        Vector<Vector2>[] path = new Vector[5];
        for(int i = 0; i < path.length; ++i){
            path[i] = new Vector<>(0);
        }

        double[] total_fitness = new double[5];
        for(int i = 0; i < total_fitness.length; ++i){
            total_fitness[i] = 0;
        }

        //start in start pos
        Vector2 last_pos = m.interest_points[m.interest_points.length-1];
        int dron = 0;
        path[dron] = new Vector<>(0);
        for(int i = 0; i < cod.get_size(); ++i){
            int index = Math.min(cod.get_value(i),m.interest_points.length-1);
            int i_index = last_pos.x*m.importanceMap.length+ last_pos.y;
            int j_index = m.interest_points[index].x*m.importanceMap.length+m.interest_points[index].y;


            cost_already_calculated[i_index][j_index] = return_new_cost(last_pos, m.interest_points[index]);
            total_fitness[dron] += dron_multiplier[dron] * cost_already_calculated[i_index][j_index].best;

            for(int l = 1; l < cost_already_calculated[i_index][j_index].path.size(); ++l){
                path[dron].add(cost_already_calculated[i_index][j_index].path.get(l));
            }

            //if codification value is greater than the number of points we set the starting point
            last_pos = m.interest_points[index];
            if(index==m.interest_points.length-1) {
                ++dron;
            }
        }
        //end in start pos
        navA_return_type ret = return_new_cost(last_pos, m.interest_points[m.interest_points.length-1]);
        total_fitness[dron] += ret.best;
        for(int l = 1; l < ret.path.size(); ++l){
            path[dron].add(ret.path.get(l));
        }

        int i = 0;
        double max = 0;
        double min = 10000;
        while(i <= dron){
            max = Math.max(max,total_fitness[i]);
            min = Math.min(min,total_fitness[i]);
            ++i;
        }
        return new fitness_return_type(path, total_fitness, max + (max-min)*0.5f);
    }

    navA_return_type return_new_cost(Vector2 last_point, Vector2 next_point){
        int i_index = last_point.x*m.importanceMap.length+ last_point.y;
        int j_index = next_point.x*m.importanceMap.length+next_point.y;
        if(already_calculated[i_index][j_index]) return cost_already_calculated[i_index][j_index];
        already_calculated[i_index][j_index] = true;
        return cost_already_calculated[i_index][j_index] = NavA.findPath(m,last_point,next_point);
        //TODO: Figure out what to do with saving the path
    }

    void reset_already_calculated_costs(){
        int total_tiles =m.importanceMap.length*m.importanceMap[0].length;
        cost_already_calculated = new navA_return_type[total_tiles][total_tiles];
        already_calculated = new boolean[total_tiles][total_tiles];
        for (int i = 0; i < cost_already_calculated.length; ++i){
            for(int j = 0; j < cost_already_calculated[0].length; ++j){
                already_calculated[i][j]=false;
            }
        }
    }

    navA_return_type[][] cost_already_calculated;
    boolean[][] already_calculated;
}
