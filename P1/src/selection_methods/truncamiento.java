package selection_methods;

import java.util.Arrays;

public class truncamiento implements selection_method {
    public int[] chooseEntities(int[] fitness, int count){
        // TODO: Separar count y criterios de truncamiento.
        int[] selected = new int[count];
        for (int i = 0; i<count; i++) {
            int max = -1;
            int maxInd = -1;
            for (int j = 0; j < fitness.length; j++) {
                if (fitness[j] > max) {
                    max = fitness[j];
                    maxInd = j;
                }
            }
            selected[i] = maxInd;
            fitness[maxInd] = -1;
        }
        return selected;
    }
}
