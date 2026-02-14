package mutation_methods;

import codification.codificacion_real;

import java.util.Random;

public class mutacion_gaussiana {
    //nextGaussian() will draw samples from a normal distribution with mean 0 and std-deviation 1,
    // so if you want mean 1 hour and std-deviation 15 minutes you'll need to call it as nextGaussian()*15+60.
    static float mid = 0;
    static float normal_deviation =1;
    float ratio;
    public mutacion_gaussiana(float ratio){
        this.ratio = ratio;
    }
    public void mutar(codificacion_real cod){
        for (int i = 0; i<cod.floatVec.length; i++){
            cod.floatVec[i] = Math.min(cod.maxValues[i%cod.getSizeGenoma()]-1,Math.max(0,cod.floatVec[i]+(float)new Random().nextGaussian()));
        }
    }
}