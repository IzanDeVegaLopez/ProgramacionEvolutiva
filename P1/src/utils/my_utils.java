package utils;

public class my_utils {
    public static void array_index_swap(int[] array, int index1, int index2){
        array[index1] += array[index2];
        array[index2] = array[index1] - array[index2];
        array[index1] -= array[index2];
    }
    public static int get_random(int non_inclusive_max){
        return (int) Math.floor(Math.random()*(non_inclusive_max));
    }
}
