package utils;

public class my_utils {
    void swap(int[] array, int index1, int index2){
        array[index1] += array[index2];
        array[index2] = array[index1] - array[index2];
        array[index1] -= array[index2];
    }
}
