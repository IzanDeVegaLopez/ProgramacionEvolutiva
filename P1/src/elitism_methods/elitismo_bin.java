package elitism_methods;

import codification.*;

import java.util.LinkedList;
import java.util.Queue;

public class elitismo_bin {
    // Número de individuos guardados en cada generación
    int bufferElemSize;
    eliteMember_bin[] stored_elite;

    boolean started;

    public elitismo_bin(int bSize){
        bufferElemSize = bSize;
        stored_elite = new eliteMember_bin[bSize];
        started = false;
    }

    public void extract_elite_bin(int[] fitness, codificacion_binaria[] cod){
        int[] mutable_fitness = fitness.clone();
        for (int i = 0; i<bufferElemSize; i++) {
            int max = -1;
            int maxidx = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (mutable_fitness[j] == -1) continue;
                if (mutable_fitness[j] > max) {
                    max = mutable_fitness[j];
                    maxidx = j;
                }
            }
            mutable_fitness[maxidx] = -1;
            stored_elite[i] = new eliteMember_bin(max, cod[maxidx]);
        }
        started = true;
    }

    public int[] introduce_elite_bin(int[] fitness, codificacion_binaria[] cod){
        int[] results = {0,0};
        if (!started) return results;

        int[] indexes_to_replace = new int[bufferElemSize];
        int acum_min = 0;
        int acum_max = 0;

        for (int i = 0; i<bufferElemSize; i++) {
            int min = Integer.MAX_VALUE;
            int minidx = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] == -1) continue;
                if (fitness[j] < min) {
                    minidx = j;
                }
            }
            acum_min += fitness[minidx];
            fitness[minidx] = -1;
            indexes_to_replace[i] = minidx;

        }
        for (int i = 0; i<bufferElemSize; i++){
            int idx = indexes_to_replace[i];
            fitness[idx] = stored_elite[i].fitness;
            cod[idx] = stored_elite[i].member;

            acum_max += stored_elite[i].fitness;
        }

        results[0] = acum_max;
        results[1] = acum_min;

        return results;
    }
}
