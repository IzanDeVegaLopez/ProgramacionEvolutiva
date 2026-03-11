package Mapas.pathing;

import utils.Vector2;

import java.util.Vector;

public class navA_return_type {
    public Vector<Vector2> path;
    public int best;
    public boolean reached;
    public navA_return_type(Vector<Vector2> v, int _best, boolean _reached){
        path = v;
        best = _best;
        reached = _reached;
    }
}
