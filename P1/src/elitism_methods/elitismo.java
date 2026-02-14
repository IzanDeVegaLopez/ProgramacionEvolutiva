package elitism_methods;

public class elitismo {
    public static int[] extract_elite_bin(int[] fitness, double ratio){
        int elite_count = (int)Math.floor(fitness.length*ratio);
        int[] mutable_fitness = fitness.clone();
        int[] selected = new int[elite_count];
        for (int i = 0; i<elite_count; i++){
            int max = -1;
            int maxind = -1;
            for (int j = 0; j<fitness.length; j++){
                if (mutable_fitness[j] > max) {
                    max = mutable_fitness[j];
                    maxind = j;
                }
            }
            selected[i] = maxind;
            mutable_fitness[maxind] = -1;
        }
        return selected;
    }
    public static eliteMember_bin[] introduce_elite_bin(int[] fitness){
        eliteMember_bin[] selected = {};
        return selected;
    }
}
