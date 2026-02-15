package elitism_methods;

import codification.*;
import fitness.FitnessReturnClass;

import java.util.LinkedList;
import java.util.Queue;

public class elitismo_bin {
    // Número de individuos guardados en cada generación
    int bufferElemSize;
    algorithm_member[] stored_elite;
    int cameras;
    int penalty;

    boolean started;

    public elitismo_bin(int bSize, int c, int p){
        bufferElemSize = bSize;
        stored_elite = new algorithm_member[bSize];
        started = false;
        cameras = c;
        penalty = p;
    }

    public void extract_elite_bin(FitnessReturnClass[] data, codificacion_binaria[] cod){
        FitnessReturnClass[] mutable_fitness = data.clone();
        int max;
        for (int i = 0; i<bufferElemSize; i++) {
            max = -1;
            int maxidx = -1;
            for (int j = 0; j < mutable_fitness.length; j++) {
                if (mutable_fitness[j].totalValue == -1) continue;
                if (mutable_fitness[j].totalValue > max) {
                    max = mutable_fitness[j].totalValue;
                    maxidx = j;
                }
            }
            stored_elite[i] = new algorithm_member(
                    data[maxidx].totalValue,
                    data[maxidx].totalNPenalties,
                    cod[maxidx]);
            mutable_fitness[maxidx].totalValue = -1;
        }
        started = true;
    }

    public elitism_output introduce_elite_bin(FitnessReturnClass[] data, codificacion_binaria[] cod){
        elitism_output results = new elitism_output();
        if (!started) return results;

        int[] indexes_to_replace = new int[bufferElemSize];
        int acum_min = 0;
        int acum_max = 0;

        for (int i = 0; i<bufferElemSize; i++) {
            int min = Integer.MAX_VALUE;
            int minidx = -1;
            for (int j = 0; j < data.length; j++) {
                if (data[j].totalValue == -1) continue;
                if (data[j].totalValue < min) {
                    minidx = j;
                    min = data[j].totalValue;
                }
            }
            acum_min += data[minidx].totalValue;
            data[minidx].totalValue = -1;
            indexes_to_replace[i] = minidx;

        }
        for (int i = 0; i<bufferElemSize; i++){
            int idx = indexes_to_replace[i];
//          fitness[idx] = stored_elite[i].fitness;
            data[idx] = new FitnessReturnClass();
            data[idx].totalValue = stored_elite[i].fitness;
            data[idx].totalNPenalties = stored_elite[i].penalty;
            cod[idx] = stored_elite[i].cod_bin;
            //cod[idx].setAllData(stored_elite[i].member.retrieveAllData());

            acum_max += stored_elite[i].fitness;
        }

        results.max_sum = acum_max;
        results.min_sum = acum_min;
        results.elements = stored_elite;
        return results;
    }
}
