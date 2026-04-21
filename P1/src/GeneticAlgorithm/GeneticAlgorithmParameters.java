package GeneticAlgorithm;

import Graphic.MapRepresentation;
import Graphic.ScrollTextField;
import Mapas.Map;
import org.math.plot.Plot2DPanel;

public class GeneticAlgorithmParameters {
    public MapRepresentation[] maps;
    public Plot2DPanel plot2d;
    public ScrollTextField log;
    public int nGen;
    public int nIndInGen;
    public float crossProbability;
    public float mutationprobability;
    //method changing things
    public int mutationType;
    public int selectionType;
    public float elite_ratio;
    public double bloating_coef;
    public int max_depth;

//    public int n_drones;
//    public int n_interest_points;
}
