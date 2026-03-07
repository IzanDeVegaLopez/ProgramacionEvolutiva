package Mapas.pathing;
import Mapas.Map;
import utils.Vector2;
import java.util.PriorityQueue;
import java.util.Vector;

public class NavA {

    /**
     * Calculates a path between two points using the A* pathfinding algorithm.
     * @param map The map to calculate a path on.
     * @param origin The starting point to calculate the path from.
     * @param destination The endpoint to calculate the path to.
     * @return An array of coordinates representing the spaces that comprise the path.
     */
    public static Vector<Vector2> findPath(Map map, Vector2 origin, Vector2 destination){
        Vector<Vector2> path = new Vector<>();

        PriorityQueue<PQElem> open_nodes = new PriorityQueue<>();

        // Add first node to visit
        open_nodes.add(new PQElem(map.importanceMap,origin,destination));

        // Reset map matrices
        map.resetTainted();
        map.resetPrevious();

        map.previous[origin.y][origin.x] = origin;

        PQElem top;
        while (!open_nodes.isEmpty()){
            top = open_nodes.poll();
            if (top.origin.equals(destination))
                break;

            map.tainted[top.origin.y][top.origin.x] = true;
            // Vertical exploration
            for (int y = -1; y < 2; y+=2){
                Vector2 newpos = new Vector2(top.origin.x, top.origin.y + y);
                // If node has been accessed or is inaccessible
                if (newpos.y >= map.ocupiedTiles.length || newpos.x >= map.ocupiedTiles[0].length
                        || newpos.y < 0 || newpos.x < 0 ||
                        map.tainted[newpos.y][newpos.x] || map.ocupiedTiles[newpos.y][newpos.x])
                    continue;
                // Add next node to visit
                open_nodes.add(new PQElem(map.importanceMap,newpos,destination));
                // Reference previous node
                map.previous[newpos.y][newpos.x] = top.origin;
            }
            // Horizontal exploration
            for (int x = -1; x < 2; x+=2){
                Vector2 newpos = new Vector2(top.origin.x + x, top.origin.y);
                // If node has been accessed or is inaccessible
                if (newpos.y >= map.ocupiedTiles.length || newpos.x >= map.ocupiedTiles[0].length
                        || newpos.y < 0 || newpos.x < 0 ||
                        map.tainted[newpos.y][newpos.x] || map.ocupiedTiles[newpos.y][newpos.x])
                    continue;
                // Add next node to visit
                open_nodes.add(new PQElem(map.importanceMap,newpos,destination));
                // Reference previous node
                map.previous[newpos.y][newpos.x] = top.origin;
            }

        }

        // If destination was not found
        if (map.previous[destination.y][destination.x].x == -1 || map.previous[destination.y][destination.x].y == -1)
            path.add(new Vector2(-1,-1));
        // Otherwise
        else {
            Vector2 currentPos = destination;
            while (!currentPos.equals(origin)) {
                path.addFirst(currentPos);
                currentPos = map.previous[currentPos.y][currentPos.x];
            }
            path.addFirst(origin);
        }

        return path;
    };

}

