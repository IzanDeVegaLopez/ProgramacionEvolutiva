package fitness;

import codification.codificacion_entera;

public class manhattan_distance_fitness_calculator implements base_fitness_calculator{
    public FitnessReturnClass calculate_fitness(codificacion_entera[] cod){
        FitnessReturnClass fit = new FitnessReturnClass(cod.length);
        fit.best_value = 1000000;
        for(int i = 0; i < cod.length; ++i){
            //TODO: return true value
            fit.totalValue[i] = i;
            if(fit.totalValue[i] < fit.best_value){
                fit.best_value = fit.totalValue[i];
            }
            fit.mid += fit.totalValue[i];
        }
        fit.mid /= cod.length;
        return fit;
    }
}
