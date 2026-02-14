package elitism_methods;

import codification.*;

import java.util.LinkedList;
import java.util.Queue;

public class elitismo_bin {
    // Número de generaciones guardadas
    int bufferLength;
    // Número de individuos guardados en cada generación
    int bufferElemSize;
    Queue<eliteMember_bin[]> stored_elite;

    // Generación actual
    int currentBuffer;

    elitismo_bin(int bLength, int bSize){
        bufferLength = bLength;
        bufferElemSize = bSize;
        currentBuffer = -1;
        stored_elite = new LinkedList<eliteMember_bin[]>();
    }

    public void extract_elite_bin(int[] fitness, codificacion_binaria[] cod){
        int[] mutable_fitness = fitness.clone();
        eliteMember_bin[] temp_array = new eliteMember_bin[bufferElemSize];
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
            temp_array[i] = new eliteMember_bin(max, cod[maxidx]);
        }
        stored_elite.add(temp_array);
    }

    public void introduce_elite_bin(int[] fitness, codificacion_binaria[] cod){
        // En el caso de que no hayamos llegado a la generación mínima para devolver élites a la población, concluimos la ejecución del método.
        currentBuffer = Math.min(currentBuffer+1,bufferLength);
        if (currentBuffer<bufferLength) return;

        int[] indexes_to_replace = new int[bufferElemSize];
        eliteMember_bin[] stored = stored_elite.poll();

        for (int i = 0; i<bufferElemSize; i++) {
            int min = Integer.MAX_VALUE;
            int minidx = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] == -1) continue;
                if (fitness[j] < min) {
                    minidx = j;
                }
            }
            fitness[minidx] = -1;
            indexes_to_replace[i] = minidx;
        }
        for (int i = 0; i<bufferElemSize; i++){
            int idx = indexes_to_replace[i];
            fitness[idx] = stored[i].fitness;
            cod[idx] = stored[i].member;
        }
    }
}
