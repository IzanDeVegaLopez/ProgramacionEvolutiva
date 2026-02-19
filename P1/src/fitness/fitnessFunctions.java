package fitness;

import Mapas.Map;
import codification.codificacion_binaria;
import codification.codificacion_real;

import java.util.ArrayList;

public class fitnessFunctions {
    //returns all tiles affected
    public static FitnessReturnClass getBinFitness(Map m, codificacion_binaria cod, boolean ponderado){
        m.resetTainted();

        FitnessReturnClass ft = new FitnessReturnClass();
        for(int i = 0; i < cod.getNElems(); ++i){
            int[] value = cod.getElemI(i);
            array_n_value resultInThisTile = getBinFitnessForOneCamera(m,value[0],value[1], ponderado);
            ft.totalValue += resultInThisTile.value;
            ft.tilesInCameraI.add(resultInThisTile.array);
            ft.totalNPenalties += resultInThisTile.penalty;
        }
        ft.totalValue = Math.max(ft.totalValue, 0);
        return ft;
    }
    public static class array_n_value{
        public int value;
        public int penalty=1;
        public ArrayList<int[]> array;
        array_n_value(int v, ArrayList<int[]> a){
            value = v; array = a;
        }
    }
    //[tile] [x==0;y==0]
    private static array_n_value getBinFitnessForOneCamera(Map m, int x, int y, boolean ponderado){
        array_n_value anv = new array_n_value(0,new ArrayList<int[]>(0));
        if(m.validTile(x,y)) {
            anv.value += m.penalty;
            anv.penalty=0;
            anv.value += ponderado ? m.importanceMap[x][y] : 1;
            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            if(!m.tainted[x][y]) {
                anv.array.add(new int[]{x, y});
                m.tainted[x][y] = true;
            }
            for (int[] delta : dir) {
                int newX,newY;
                for (int i = 1; i <= m.visionRange && m.validTile(newX = x+delta[0]*i,newY=y+delta[1]*i); ++i) {
                    if(!m.tainted[newX][newY]) {
                        anv.array.add(new int[]{newX, newY});
                        anv.value += ponderado ? m.importanceMap[newY][newX] : 1;
                        m.tainted[newX][newY] = true;
                    }
                }
            }
        }
        return anv;
    }

    public static FitnessReturnClass getFloatFitness(Map m, codificacion_real cod, boolean ponderado){
        m.resetTainted();

        FitnessReturnClass ft = new FitnessReturnClass();
        for(int i = 0; i < cod.getNElems(); ++i){
            float[] value = cod.getElemI(i);
            array_n_value resultInThisTile = getFloatFitnessForOneCamera(m,value[0],value[1], value[2],ponderado);
            //IO.println("\nCámara: " + i+"== Ángulo: " + value[2]);
            ft.totalValue += resultInThisTile.value;
            ft.tilesInCameraI.add(resultInThisTile.array);
            ft.totalNPenalties += resultInThisTile.penalty;
        }
        return ft;
    }
    public static array_n_value getFloatFitnessForOneCamera(Map m, float x1, float y1, float orientation, boolean ponderado) {
        array_n_value anv = new array_n_value(0,new ArrayList<int[]>(0));
        ArrayList<int[]> tiles = new ArrayList<>(0);
        if (m.validTile((int) (x1),(int) (y1))) {
            anv.penalty = 0;
            anv.value+=m.penalty;
            float halfAngle = m.apertureAngle/2;
            for (float j = orientation - halfAngle;
                 j < orientation + halfAngle;
                 j = j + 5) {
                double rad = Math.PI * j / 180.0f;
                double x2 = x1+(m.visionRange * Math.cos(rad));
                double y2 = y1+(m.visionRange * Math.sin(rad));
                //IO.print("["+x1+','+y1+"] --> ["+x2+','+y2+"]\n");
                // 1. Calcular distancia total
                double dist = m.visionRange;
// Optimización: Si está muy cerca, asumimos que se ve
                //if (dist < 0.1) return true;
// 2. Definir la resolución del paso (20 pasos por celda = 0.05 de avance)
                int pasos = (int) (dist * 20.0);
                double dx = (x2 - x1) / pasos;
                double dy = (y2 - y1) / pasos;
                double px = x1;
                double py = y1;
// Guardamos la celda anterior para detectar cambios de diagonal
                int prevX = (int) x1;
                int prevY = (int) y1;
// 3. Recorrer el rayo paso a paso
                for (int i = 0; i < pasos; i++) {
                    px += dx;
                    py += dy;
                    int cx = (int) px; // Columna actual
                    int cy = (int) py; // Fila actual
// A. Chequeo de límites del mapa
// B. Chequeo de Muro Directo (0 es muro en tus mapas PDF)
                    if (!m.validTile(cx, cy)) break;
// C. LÓGICA ANTICLIPPING (Bloqueo de esquinas)
// Si hemos cambiado de columna Y de fila a la vez...
                    if (cx != prevX && cy != prevY) {
// Verificamos los dos vecinos que forman la esquina
// Si ambos son muros (0), entonces la esquina está cerrada.
                        if (m.ocupiedTiles[cx][prevY] && m.ocupiedTiles[prevX][cy]) {
                            break;
                        }
                    }
// Actualizamos la celda previa
                    if (!m.tainted[cx][cy]) {
                        anv.array.add(new int[]{cx, cy});
                        m.tainted[cx][cy] = true;
                        anv.value += ponderado? m.importanceMap[cy][cx] : 1;
                        //IO.print("["+cx+','+cy+"] , ");
                    }
                    prevX = cx;
                    prevY = cy;
                }
// Si llegamos aquí, el camino está despejado
            }
        }
        return anv;
    }

}
