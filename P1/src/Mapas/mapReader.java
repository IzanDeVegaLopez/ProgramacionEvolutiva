package Mapas;

import java.io.FileNotFoundException;
import java.lang.String;
import java.io.File;
import java.util.Scanner;

public class mapReader {

    static int [][][] importanceMaps = {
        //museo
        new int [][] {
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 5, 5, 5, 1, 1, 1, 0, 1}, // Pasillo Norte
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 5, 5, 5, 1, 1, 1}, // Pasillo Centro
            {0, 1, 1, 1, 0, 10, 1, 1, 1, 1}, // <--- JOYA (10)
            {1, 1, 0, 1, 1, 5, 1, 0, 1, 1}, // Conexión
            {1, 1, 1, 1, 1, 5, 1, 1, 1, 1}, // Conexión
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 5, 5, 5, 0, 5, 5, 5, 1}, // Pasillo Sur
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1}
        },
            //pasillos
        new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 20, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 20, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 20, 0, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
                {0, 20, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 20, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        },
            //super
        new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 0}, // Entrada
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 15, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0}, // Hotspot Izq
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0}, // Pasillo Central
                {0, 1, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 15, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0}, // Hotspot Izq
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 0}, // Cajas
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        }
    };

    static String[] mapFiles = {"museo.txt", "pasillos.txt", "supermercado.txt"};
    public static int MUSEO = 0;
    public static int PASILLOS = 1;
    public static int SUPERMERCADO = 2;
    public static Map readMap(int mapID){
        File f = new File("src/Mapas/"+mapFiles[mapID]);
        boolean [][] b;
        int nCam, rng;
        float angle;

        try (Scanner myReader = new Scanner(f)) {
            while(!myReader.hasNextInt())
                myReader.next();
            nCam = myReader.nextInt();
            while(!myReader.hasNextInt())
                myReader.next();
            rng = myReader.nextInt();
            while(!myReader.hasNextFloat())
                myReader.next();
            angle = myReader.nextFloat();

            while(!myReader.hasNextInt())
                myReader.next();
            int height = myReader.nextInt(),
                    width = myReader.nextInt();

            b = new boolean[width][height];
            for(int i = 0; i < height; ++i){
                for(int j = 0; j < width; ++j){
                    b[j][i] = 1==myReader.nextInt();
                }
            }
            return new Map(importanceMaps[mapID], b, nCam,rng, angle);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}
