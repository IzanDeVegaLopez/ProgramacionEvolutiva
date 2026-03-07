package Mapas.pathing;

import utils.Vector2;

import java.util.Vector;

public class PQElem implements Comparable<PQElem> {
    public Vector2 origin;
    //Destination is implied in PQElem's context.
    public int heuristic;
    public int stepCount;

    PQElem(int [][] map, Vector2 o, Vector2 d){
        origin = o;
        heuristic = distance_heuristic(o,d) + map[origin.y][origin.x];
        stepCount = 0;
    }
    PQElem(int [][] map, Vector2 o, Vector2 d, int sc){
        origin = o;
        stepCount = sc+1;
        heuristic = distance_heuristic(o,d) + map[origin.y][origin.x] + stepCount;
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
