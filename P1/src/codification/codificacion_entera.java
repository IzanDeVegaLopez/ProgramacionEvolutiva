package codification;

import java.util.BitSet;

public class codificacion_entera {
    //True values
    private int[] values;
    //Stores in index i the index of i in the value array
    private int[] conflict_point;
    //How many elements remain to be assigned
    int free_values;
    public codificacion_entera(int n_elems){
        values = new int[n_elems];
        conflict_point = new int[n_elems];
        for(int i = 0; i < n_elems; ++i){
            conflict_point[i]=-1;
            values[i]=-1;
        }
        free_values = n_elems;
    }
    //Sets the element i to value i for each element
    public void initialize_with_stair_shape() {
        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
            conflict_point[i] = i;
        }
        free_values = 0;
    }
    public int get_size(){
        return values.length;
    }
    public int get_free(){return free_values;}
    public void set_value(int index, int new_value){
        if(values[index]!=-1){
            IO.print("Se ha intentado asignar un elemento que ya estaba asignado");
        }
        values[index] = new_value;
        conflict_point[new_value]=index;
        --free_values; //MUST NEVER BE INFERIOR TO -1
    }
    public int get_value(int index){
        return values[index];
    }
    //searches for the number
    public boolean is_contained(int num){
        return conflict_point[num]!=-1;
    }
    public int get_conflict_index(int num){
        return conflict_point[num];
    }

    public int[] get_values(){
        return values;
    }
    public int[] get_conflict_point(){
        return conflict_point;
    }

    /**
     * turns this into the cod passed
     */
    public void copy(codificacion_entera cod){
        values = cod.get_values().clone();
        conflict_point = cod.get_conflict_point().clone();
    }

    private void swap(int index1, int index2){

    }

    public void insert(int element_to_displace_index, int new_index){

    }
}
