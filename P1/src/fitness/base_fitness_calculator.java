package fitness;

import Mapas.Map;
import codification.codificacion_entera;

public interface base_fitness_calculator {
    public FitnessReturnClass calculate_fitness(Map m, codificacion_entera[] c);
}
