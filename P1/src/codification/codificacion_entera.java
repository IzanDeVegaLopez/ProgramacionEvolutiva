package codification;

public class codificacion_entera {
    private int[] values;
    codificacion_entera(int n_elems){
        values = new int[n_elems];
        for(int i = 0; i < n_elems; ++i){
            values[i]=i;
        }
    }
    int get_size(){
        return values.length;
    }
    void set_value(int index, int new_value){
        values[index] = new_value;
    }
    int get_value(int index){
        return values[index];
    }
}
