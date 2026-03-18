package fitness;

import utils.Vector2;

import java.util.Vector;

public class fitness_return_type {
    public Vector<Vector2>[] path;
    public double[] route_duration_per_drone;
    public double value;
    public fitness_return_type(Vector<Vector2>[] _path, double[] d, double _value){
        path = _path;
        value = _value;
        route_duration_per_drone = d;
    }
    public fitness_return_type(int n_drones){
        value = 0;
        route_duration_per_drone = new double[n_drones];
        //path = new Vector<Vector2>(n_drones);
    }
}
