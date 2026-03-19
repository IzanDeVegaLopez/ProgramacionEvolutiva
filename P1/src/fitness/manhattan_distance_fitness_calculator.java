package fitness;

import Mapas.Map;
import Mapas.pathing.AStar_return_type;
import codification.codificacion_entera;
import utils.Vector2;

import java.util.Collections;
import java.util.Vector;

import static Mapas.pathing.AStar_redone_main_body.find_path;

public class manhattan_distance_fitness_calculator implements base_fitness_calculator{
    public static float[] dron_multiplier = {1/1.5f, 1, 1/0.7f, 1/1.2f, 2};
    Map m;
    public manhattan_distance_fitness_calculator(Map _m){
        m = _m;
        reset_already_calculated_costs();
    }
    public FitnessReturnClass calculate_fitness(codificacion_entera[] cod){
        FitnessReturnClass fit = new FitnessReturnClass(cod.length);
        fit.best_fitness_result.value = Double.POSITIVE_INFINITY;
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
        int depot_index = m.interest_points.length-1;

        Vector<Vector2>[] path = new Vector[5];
        for(int i = 0; i < path.length; ++i){
            path[i] = new Vector<>(0);
        }

        double[] total_fitness = new double[5];
        for(int i = 0; i < total_fitness.length; ++i){
            total_fitness[i] = 0;
        }

        //start in start pos
        Vector2 last_pos = m.interest_points[depot_index];
        int dron = 0;
        path[dron] = new Vector<>(0);
        for(int i = 0; i < cod.get_size(); ++i){
            int index = Math.min(cod.get_value(i),depot_index);
            AStar_return_type nav = return_new_cost(last_pos, m.interest_points[index]);

            total_fitness[dron] += dron_multiplier[dron] * nav.value;

            for(int l = 0; l < nav.path.size(); ++l){
                path[dron].add(nav.path.get(l));
            }

            //if codification value is greater than the number of points we set the starting point
            last_pos = m.interest_points[index];
            if (index == m.interest_points.length-1) {
                AStar_return_type retToBase = return_new_cost(last_pos, m.interest_points[depot_index]);
                total_fitness[dron] += dron_multiplier[dron] * retToBase.value;
                for(int l = 0; l < retToBase.path.size(); ++l){
                    path[dron].add(retToBase.path.get(l));
                }
                dron++;
                last_pos = m.interest_points[depot_index];
            }
        }
        //end in start pos
        AStar_return_type ret = return_new_cost(last_pos, m.interest_points[m.interest_points.length-1]);
        total_fitness[dron] += dron_multiplier[dron]* ret.value;
        for(int l = 0; l < ret.path.size(); ++l){
            path[dron].add(ret.path.get(l));
        }

        int i = 0;
        double max = 0;
        double min = Double.POSITIVE_INFINITY;
        while(i <= dron){
            Collections.reverse(path[i]);
            max = Math.max(max,total_fitness[i]);
            min = Math.min(min,total_fitness[i]);
            ++i;
        }
        return new fitness_return_type(path, total_fitness, max + (max-min)*0.5f);
    }

    AStar_return_type return_new_cost(Vector2 last_point, Vector2 next_point){
        int width = m.importanceMap[0].length;
        int i_index = last_point.y * width + last_point.x;
        int j_index = next_point.y * width + next_point.x;

        if(!already_calculated[i_index][j_index]) {
            cost_already_calculated[i_index][j_index] = find_path(m, last_point, next_point);
            already_calculated[i_index][j_index] = true;
        }

        return cost_already_calculated[i_index][j_index];
    }

    /*
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
    */

    void reset_already_calculated_costs(){
        int total_tiles = m.importanceMap.length*m.importanceMap[0].length;
        cost_already_calculated = new AStar_return_type[total_tiles][total_tiles];
        already_calculated = new boolean[total_tiles][total_tiles];
        for (int i = 0; i < already_calculated.length; ++i){
            for(int j = 0; j < already_calculated[0].length; ++j){
                already_calculated[i][j]=false;
            }
        }
    }

    AStar_return_type[][] cost_already_calculated;
    boolean[][] already_calculated;
}
