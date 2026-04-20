package elitism_methods;

import java.util.PriorityQueue;
import Error.UnreachableCode;

public class elitism {
    class value_n_index{
        double value;
        int idx;
        value_n_index(double v, int i){
            value = v;
            idx = i;
        }
    };
    public int[] choose_elite(int nElite, double[] fitness) throws Exception{
        if(nElite > fitness.length)
            throw new UnreachableCode("Error: More elements than existing must be chosen in choose_elite. fitness cod is not large enough");

        PriorityQueue<value_n_index> pq = new PriorityQueue<>(
                (a,b) -> Double.compare(b.value,a.value)
        );
        for(int i = 0; i < fitness.length; ++i){
            pq.add(new value_n_index(fitness[i], i));
        }
        int[] best = new int[nElite];
        for(int i = 0; i < nElite; ++i){
            best[i] = pq.poll().idx;
        }
        return best;
    }
    public int[] choose_worst(int nElite, double[] fitness){
        PriorityQueue<value_n_index> pq = new PriorityQueue<>(
                (a,b) -> Double.compare(a.value, b.value)
        );
        for(int i = 0; i < fitness.length; ++i){
            pq.add(new value_n_index(fitness[i], i));
        }
        int[] worst = new int[nElite];
        for(int i = 0; i < nElite; ++i){
            worst[i] = pq.poll().idx;
        }
        return worst;
    }
}
