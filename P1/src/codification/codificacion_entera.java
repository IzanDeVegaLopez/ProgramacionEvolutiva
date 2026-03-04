package codification;

import java.util.BitSet;

public class codificacion_entera {
    private int[] values;
    private int[] conflict_point;
    public codificacion_entera(int n_elems){
        values = new int[n_elems];
        conflict_point = new int[n_elems];
        for(int i = 0; i < n_elems; ++i){
            conflict_point[i]=-1;
        }
    }
    //Sets the element i to value i for each element
    public void initialize_with_stair_shape() {
        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
            conflict_point[i] = i;
        }
    }
    public int get_size(){
        return values.length;
    }
    public void set_value(int index, int new_value){
        values[index] = new_value;
        conflict_point[new_value]=index;
    }
    public int get_value(int index){
        return values[index];
    }
    public boolean is_contained(int num){
        return conflict_point[num]!=-1;
    }
    public int get_conflict_index(int num){
        return conflict_point[num];
    }
}
