package utils;
import Error.UnreachableCode;

import java.util.ArrayList;

public class my_utils {
    public static void array_index_swap(int[] array, int index1, int index2){
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
    public static <T extends Comparable<T>> T get_min(ArrayList<T> array, T positive_infinity)throws Exception{
        if(array.isEmpty()){
            throw new UnreachableCode("array length was 0 in my_utils.get_min");
        }
        T min = positive_infinity;
        for(T elem : array){
            if(elem.compareTo(min) < 0){
                min = elem;
            }
        }
        return min;
    }
    public static int get_random(int non_inclusive_max){
        return (int) Math.floor(Math.random()*(non_inclusive_max));
    }
}
