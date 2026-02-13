package elitism_methods;

public class elitismo {
    public static eliteMember_bin[] extract_elite_bin(int[] fitness, double ratio){
        int elite_count = (int)Math.floor(fitness.length*ratio);
        eliteMember_bin[] selected = new eliteMember_bin[elite_count];

        return selected;
    }
    public static eliteMember_bin[] introduce_elite_bin(int[] fitness){
        eliteMember_bin[] selected = {};
        return selected;
    }
}
