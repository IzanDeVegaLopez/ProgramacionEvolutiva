package Mapas.pathing;
import Mapas.Map;
import utils.Vector2;
import java.util.PriorityQueue;

public class NavA {

    /**
     * Calculates a path between two points using the A* pathfinding algorithm.
     * @param map The map to calculate a path on.
     * @param origin The starting point to calculate the path from.
     * @param destination The endpoint to calculate the path to.
     * @return An array of coordinates representing the spaces that comprise the path.
     */
    public static Vector2[] distance(Map map, Vector2 origin, Vector2 destination){
        Vector2[] path = new Vector2[0];

        PriorityQueue<PQElem> open_nodes = new PriorityQueue<>();
        open_nodes.add(new PQElem(map.importanceMap,origin,destination));
        PriorityQueue<PQElem> closed_nodes = new PriorityQueue<>();

        return path;
    };

}

