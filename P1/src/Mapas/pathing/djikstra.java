package Mapas.pathing;

import java.util.PriorityQueue;
import utils.Vector2;

public class djikstra {
    static Vector2[] ady = {
        new Vector2(-1,0),
        new Vector2(1,0),
        new Vector2(0,1),
        new Vector2(0,-1)
    };
    pathing_algorithm_return_type djikstra_algorithm(int[][] map){
        int total_vertex = 0;
        if(map.length > 0) {
            total_vertex = map.length * map[0].length;
        }

        pathing_algorithm_return_type ret = new pathing_algorithm_return_type();
        ret.previous_vertex = new Vector2[total_vertex][total_vertex];
        ret.cost = new int[total_vertex][total_vertex];
        
        //Do djikstra from vertex i
        for(int i = 0; i < total_vertex; ++i){
            boolean[][] tainted = new boolean[map.length][map[0].length];
            for(int a = 0; a < tainted.length; ++a)
                for(int b = 0; b < tainted[0].length; ++b)
                    tainted[a][b] = false;

            PriorityQueue<Djikstra_PQElem> pq = new PriorityQueue<>();
            int x = total_vertex / map.length;
            int y = total_vertex % map.length;
            pq.add(new Djikstra_PQElem(new Vector2(x,y), new Vector2(x,y), 0));
            Djikstra_PQElem current;
            while((current = pq.poll()) != null){
                if(tainted[current.current.x][current.current.y]) continue;

                int position_index = current.current.x*map[0].length+current.current.y;
                tainted[current.current.x][current.current.y] = true;
                ret.cost[position_index][i] = current.cost;
                ret.previous_vertex[position_index][i] = current.previous;

                for(Vector2 delta : ady){
                    Vector2 new_pos = new Vector2(delta.x + x, delta.y + y);
                    //If valid position and not visited
                    if(new_pos.x >= 0 && new_pos.x < map.length &&
                        new_pos.y >= 0 && new_pos.y < map[0].length &&
                        !tainted[new_pos.x][new_pos.y] &&
                        map[new_pos.x][new_pos.y]>0){
                        pq.add(
                            new Djikstra_PQElem(
                                new Vector2(new_pos.x, new_pos.y),
                                new Vector2(current.current.x, current.current.y),
                                current.cost + map[new_pos.x][new_pos.y]
                            )
                        );
                    }
                }
            }
        }
        return ret;
    }
}
