package elitism_methods;

import java.util.PriorityQueue;

public class elitism {
    class value_n_index{
        int value;
        int idx;
        value_n_index(int v, int i){
            value = v;
            idx = i;
        }
    };
    public int[] choose_elite(int nElite, int[] fitness){
        PriorityQueue<value_n_index> pq = new PriorityQueue<>((a,b)-> b.value - a.value);
        for(int i = 0; i < fitness.length; ++i){
            pq.add(new value_n_index(fitness[i], i));
        }
        int[] best = new int[nElite];
        for(int i = 0; i < nElite; ++i){
            best[i] = pq.poll().idx;
        }
        return best;
    }
    public int[] choose_worst(int nElite, int[] fitness){
        PriorityQueue<value_n_index> pq = new PriorityQueue<>((a,b)-> a.value - b.value);
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
