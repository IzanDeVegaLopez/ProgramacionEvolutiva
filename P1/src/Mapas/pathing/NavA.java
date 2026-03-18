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
    public static navA_return_type findPath(Map map, Vector2 origin, Vector2 destination){
        Vector<Vector2> path = new Vector<>();
        int[][] reached_matrix = new int[map.tainted.length][map.tainted[0].length];
        for(int i = 0; i < map.tainted.length; ++i){
            for(int j = 0; j < map.tainted[0].length; ++j){
                reached_matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        int best = -1;
        // Reset map matrices
        //map.resetTainted();
        map.resetPrevious();

        if(!map.validTile(origin.x, origin.y)) return new navA_return_type(path,best, false);

        PriorityQueue<PQElem> open_nodes = new PriorityQueue<>();

        // Add first node to visit
        open_nodes.add(new PQElem(origin,destination, origin));

        //map.previous[destination.y][destination.x] = new Vector2(-1,-1);

        PQElem top;
        while (!open_nodes.isEmpty()){
            top = open_nodes.poll();
            if(reached_matrix[top.origin.y][top.origin.x] <= top.stepCount) continue;

            reached_matrix[top.origin.y][top.origin.x] = top.stepCount;
            map.previous[top.origin.y][top.origin.x] = new Vector2(top.previous_tile.x, top.previous_tile.y);

            if (top.heuristic > reached_matrix[destination.y][destination.x]) {
                best = top.stepCount;
                break;
            }
            // Vertical exploration
            for (int y = -1; y < 2; y+=2){
                Vector2 newpos = new Vector2(top.origin.x, top.origin.y + y);
                // If node has been accessed or is inaccessible

                if (!map.validTile(newpos.x,newpos.y))
                    continue;

                int extra_cost = 0;
                if(map.has_camera(newpos.x,newpos.y) && !destination.equals(newpos)) extra_cost = map.penalty;
                int step_count = extra_cost + top.stepCount + map.importanceMap[newpos.y][newpos.x];
                // Add next node to visit
                if(step_count < reached_matrix[newpos.y][newpos.x])
                    open_nodes.add(new PQElem(newpos,destination, top.origin, step_count));
            }
            // Horizontal exploration
            for (int x = -1; x < 2; x+=2){
                Vector2 newpos = new Vector2(top.origin.x + x, top.origin.y);
                // If node has been accessed or is inaccessible
                if (!map.validTile(newpos.x,newpos.y))
                    continue;

                int extra_cost = 0;
                if(map.has_camera(newpos.x,newpos.y) && !destination.equals(newpos)) extra_cost = map.penalty;
                int step_count = extra_cost + top.stepCount + map.importanceMap[newpos.y][newpos.x];
                // Add next node to visit
                if(step_count < reached_matrix[newpos.y][newpos.x])
                    open_nodes.add(new PQElem(newpos,destination, top.origin, step_count));
            }

        }

        // If destination was not found
        if (map.previous[destination.y][destination.x].x == -1 || map.previous[destination.y][destination.x].y == -1)
            return new navA_return_type(path,best,false);
        // Otherwise
        else {
            Vector2 currentPos = destination;
            while (!currentPos.equals(origin)) {
                path.addFirst(currentPos);
                currentPos = map.previous[currentPos.y][currentPos.x];
            }
            path.addFirst(origin);
        }

        return new navA_return_type(path, best, true);
    }

}

