package Mapas.pathing;


import Mapas.Map;
import utils.Vector2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import static Mapas.pathing.AStar_aux_functions.*;

public class AStar_redone_main_body {
    public static AStar_return_type find_path(Map m, Vector2 start, Vector2 goal){
        AStar_return_type ret = new AStar_return_type();

        AStar_node start_node = new AStar_node(start,null, calculate_heuristic(start,goal), 0);

        //Comparator<AStar_node> idComparator = Comparator.comparing(AStar_node::heuristica);

        PriorityQueue<AStar_node> pq = new PriorityQueue<>();
        pq.add(start_node);
        HashMap<Vector2, AStar_node> open_map = new HashMap<>(0);
        open_map.put(start, start_node);
        HashSet<Vector2> closed_set = new HashSet<>(0);

        while(!pq.isEmpty()){
            AStar_node current_node = pq.poll();
            if(current_node.current_pos.equals(goal)){
                ret.value = current_node.real_acumulado;
                ret.path = reconstruct_path(open_map, goal);
                return ret;
            }

            closed_set.add(current_node.current_pos);

            for( Vector2 neigh : get_valid_neighbours(current_node.current_pos, m)){
                if(closed_set.contains(neigh)) continue;
                double path_cost =
                        current_node.real_acumulado +
                        m.get_tile_cost(neigh);

                if(!open_map.containsKey(neigh)){
                    AStar_node neighbor_node =
                            new AStar_node(
                                    neigh,
                                    current_node.current_pos,
                                    calculate_heuristic(neigh, goal),
                                    path_cost
                            );
                    pq.add(neighbor_node);
                    open_map.put(neigh, neighbor_node);
                }else if (path_cost < open_map.get(neigh).real_acumulado){
                    AStar_node neighbor_node = open_map.get(neigh);
                    neighbor_node.real_acumulado = path_cost;
                    neighbor_node.heurística = path_cost + neighbor_node.estimado;
                    neighbor_node.parent_pos = current_node.current_pos.clone();
                }
            }
        }
        return ret;
    }
}
