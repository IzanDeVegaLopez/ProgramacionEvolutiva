package fitness;

import Mapas.Map;
import codification.codificacion_binaria;

import java.util.ArrayList;

public class fitnessFunctions {
    //returns all tiles affected
    public static FitnessReturnClass getBinFitness(Map m, codificacion_binaria cod){
        m.resetTainted();

        FitnessReturnClass ft = new FitnessReturnClass();
        for(int i = 0; i < cod.getNElems(); ++i){
            int[] value = cod.getElemI(i);
            array_n_value resultInThisTile = getBinFitnessForOneCamera(m,value[0],value[1]);
            ft.totalValue += resultInThisTile.value;
            ft.tilesInCameraI.add(resultInThisTile.array);
        }
        ft.totalValue = Math.max(ft.totalValue, 0);
        return ft;
    }
    public static class array_n_value{
        public int value;
        public ArrayList<int[]> array;
        array_n_value(int v, ArrayList<int[]> a){
            value = v; array = a;
        }
    }
    //[tile] [x==0;y==0]
    private static array_n_value getBinFitnessForOneCamera(Map m, int x, int y){
        array_n_value anv = new array_n_value(0,new ArrayList<int[]>(0));
        if(m.validTile(x,y)) {
            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            anv.array.add(new int[]{x, y});
            m.tainted[x][y] = true;
            for (int[] delta : dir) {
                int newX,newY;
                for (int i = 1; i <= m.visionRange && m.validTile(newX = x+delta[0]*i,newY=y+delta[1]*i); ++i) {
                    if(!m.tainted[newX][newY]) {
                        anv.array.add(new int[]{newX, newY});
                        m.tainted[newX][newY] = true;
                    }
                }
            }
            anv.value = anv.array.size();
        }
        return anv;
    }
}
