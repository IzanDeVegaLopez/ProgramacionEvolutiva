package Mapas.pathing;

import utils.Vector2;

import java.util.Vector;

public class PQElem implements Comparable<PQElem> {
    public Vector2 origin;
    public Vector2 previous_tile;
    //Destination is implied in PQElem's context.
    public int heuristic;
    public int stepCount;

    PQElem(Vector2 o, Vector2 d, Vector2 _previous_tile){
        origin = o;
        heuristic = distance_heuristic(o,d);
        stepCount = 0;
        previous_tile = _previous_tile;
    }
    PQElem(Vector2 o, Vector2 d, Vector2 _previous_tile, int sc){
        origin = o;
        stepCount = sc;
        heuristic = distance_heuristic(o,d) + stepCount;
        previous_tile = _previous_tile;
    }

    /**
     * Calculates a predicted number of spaces to traverse between two points. Parameters are interchangeable.
     * @param origin The starting point from which to calculate the predicted distance.
     * @param destination The endpoint towards which to calculate the predicted distance.
     * @return The minimum number of spaces required to arrive at one point from the other.
     */
    public static int distance_heuristic(Vector2 origin, Vector2 destination){
        return Math.abs(origin.x - destination.x) + Math.abs(origin.y - destination.y);
    }

    public int compareTo(PQElem other){
        return Integer.compare(heuristic,other.heuristic);
    }
}
