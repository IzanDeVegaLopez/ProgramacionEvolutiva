package GeneticAlgorithm;

import Mapas.Map;
import codification.codificacion_binaria;
import org.math.plot.Plot2DPanel;

public class GeneticAlgorithm {
    codificacion_binaria[] cod;
    int currentGen = 0;
    public GeneticAlgorithm(GeneticAlgorithmParameters p){
        startGeneticAlgorithm(p);
        loopGeneticAlgorithm(p);
        endGeneticAlgorithm(p);
    }
    public void startGeneticAlgorithm(GeneticAlgorithmParameters p){
        currentGen = 0;
        cod = new codificacion_binaria[p.nIndInGen];
        for(int i = 0; i < p.nIndInGen; ++i){
            cod[i] = new codificacion_binaria(p.m.ocupiedTiles.length, p.m.ocupiedTiles[0].length, p.m.nCamaras);
        }
    }
    public void loopGeneticAlgorithm(GeneticAlgorithmParameters p){
        while(currentGen < p.nGen) {
            //FITNESS
            //PINTAR
            //SELECCIÓN
            //CRUCE
            //MUTACIÓN
            ++currentGen;
        }
    }
    public void endGeneticAlgorithm(GeneticAlgorithmParameters p){
        //DONT KNOW, WHATEVER
    }
}
