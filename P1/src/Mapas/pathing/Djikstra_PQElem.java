package Mapas.pathing;

import utils.Vector2;

public class Djikstra_PQElem implements Comparable<Djikstra_PQElem> {
    public Vector2 current;
    public Vector2 previous;
    public int cost;

    Djikstra_PQElem(Vector2 c, Vector2 p){
        current = c;
        previous = p;
        cost = 0;
    }
    Djikstra_PQElem(Vector2 c, Vector2 p, int _cost){
        current = c;
        previous = p;
        cost = _cost;
    }
    public int compareTo(Djikstra_PQElem other){
        return Integer.compare(cost,other.cost);
    }
}
