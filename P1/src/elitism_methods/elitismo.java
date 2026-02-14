package elitism_methods;

public class elitismo {
    public static elitismReturnValue extract_elite_bin(int[] fitness, int nElite){
        int[] mutable_fitness = fitness.clone();
        int[] maxSelected = new int[nElite];
        int[] minSelected = new int[nElite];
        for (int i = 0; i<nElite; i++){
            int max = -1; int maxidx = -1;
            int min = 100000; int minidx = -1;
            for (int j = 0; j<fitness.length; j++){
                if(mutable_fitness[j]==-1) continue;
                if (mutable_fitness[j] > max) {
                    max = mutable_fitness[j];
                    maxidx = j;
                }
                else if(mutable_fitness[j] < min){
                    min = mutable_fitness[j];
                    minidx = j;
                }
            }
            maxSelected[i] = maxidx;
            minSelected[i] = minidx;
            mutable_fitness[maxidx] = -1;
            mutable_fitness[minidx] = -1;
        }
        return new elitismReturnValue(minSelected,maxSelected);
    }
    public static eliteMember_bin[] introduce_elite_bin(int[] fitness){
        eliteMember_bin[] selected = {};
        return selected;
    }
}
