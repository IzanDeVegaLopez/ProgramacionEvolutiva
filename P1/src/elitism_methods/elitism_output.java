package elitism_methods;

public class elitism_output {
    public algorithm_member[] elements;
    public int max_sum;
    public int min_sum;
    public elitism_output() {
        elements = new algorithm_member[0];
        max_sum = min_sum = 0;
    }
    public elitism_output(algorithm_member[] e, int x, int n){
        elements = e;
        max_sum = x;
        min_sum = n;
    }
}
