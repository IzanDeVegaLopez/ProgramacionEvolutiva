package GeneticAlgorithm;

import Graphic.MapRepresentation;
import Graphic.ScrollTextField;
import Mapas.Map;
import org.math.plot.Plot2DPanel;

public class GeneticAlgorithmParameters {
    public MapRepresentation m;
    public Plot2DPanel plot2d;
    public ScrollTextField log;
    public int nGen;
    public int nIndInGen;
    public float crossProbability;
    public float mutationprobability;
    //method changing things
    public int crossType;
    public int mutationType;
    public int selectionType;
    public float elite_ratio;
    public boolean isPonderado;

    public int n_drones;
    public int n_interest_points;
}
