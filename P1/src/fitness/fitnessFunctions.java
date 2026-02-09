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
            int x=0,y=0;
            //Contruye números a partir de binarios
            //Presupone que el bit menos significativo está a la izquierda
            for(int ix = 0; ix < cod.sizeParts[0]; ++ix){
                x += (cod.bitset.get(cod.getSizeElem()*i+ix) ? 1 : 0) << ix;
            }
            for(int iy = 0; iy < cod.sizeParts[1]; ++iy){
                y += (cod.bitset.get(cod.sizeParts[0] + cod.getSizeElem()*i+iy) ? 1 : 0) << iy;
            }
            ArrayList<int[]> resultInThisTile = getBinFitnessForOneCamera(m,x,y);
            ft.totalCameras += resultInThisTile.size();
            ft.tilesInCameraI.add(resultInThisTile);
        }
        return ft;
    }
    //[tile] [x==0;y==0]
    private static ArrayList<int[]> getBinFitnessForOneCamera(Map m, int x, int y){
        ArrayList<int[]> retVal = new ArrayList<int[]>(0);
        if(m.validTile(x,y)) {
            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            retVal.add(new int[]{x, y});
            m.tainted[x][y] = true;
            for (int[] delta : dir) {
                int newX,newY;
                for (int i = 1; i < m.visionRange && m.validTile(newX = x+delta[0]*i,newY=y+delta[1]*i); ++i) {
                    if(!m.tainted[newX][newY]) {
                        retVal.add(new int[]{newX, newY});
                        m.tainted[newX][newY] = true;
                    }
                }
            }
        }
        return retVal;
    }
}
