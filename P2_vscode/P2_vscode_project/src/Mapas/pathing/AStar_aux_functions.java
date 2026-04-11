package Mapas.pathing;

import Mapas.Map;
import utils.Vector2;

import java.util.HashMap;
import java.util.Vector;

public class AStar_aux_functions {
    public static int calculate_heuristic(Vector2 pos1, Vector2 pos2){
        return Math.abs(pos1.x-pos2.x) + Math.abs(pos1.y-pos2.y);
    }
    public static Vector<Vector2> get_valid_neighbours(Vector2 current_pos, Map m){
        Vector2 Ltile = new Vector2(current_pos.x-1, current_pos.y);
        Vector2 Rtile = new Vector2(current_pos.x+1, current_pos.y);
        Vector2 Utile = new Vector2(current_pos.x, current_pos.y-1);
        Vector2 Dtile = new Vector2(current_pos.x, current_pos.y+1);

        Vector<Vector2> ret = new Vector<>(0);
        if(m.validTile(Ltile)) ret.add(Ltile);
        if(m.validTile(Rtile)) ret.add(Rtile);
        if(m.validTile(Utile)) ret.add(Utile);
        if(m.validTile(Dtile)) ret.add(Dtile);

        return ret;
    }

    public static Vector<Vector2> reconstruct_path(HashMap<Vector2, AStar_node> m, Vector2 end_point){
        Vector<Vector2> path = new Vector<>(0);

        Vector2 current = end_point;
        while(current != null){
            path.add(current);
            current = m.get(current).parent_pos;
        }
        return path;
    }
}
