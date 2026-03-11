package fitness;

import utils.Vector2;

import java.util.Vector;

public class fitness_return_type {
    public Vector<Vector2>[] path;
    public double value;
    public fitness_return_type(Vector<Vector2>[] _path, double _value){
        path = _path;
        value = _value;
    }
    public fitness_return_type(){
        value = 0;
    }
}
