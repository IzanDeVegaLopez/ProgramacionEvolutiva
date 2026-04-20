package selection_methods;

import java.util.ArrayList;

import static utils.my_utils.get_min;

public class tabla_frecuencias {
    public double[] frec_absoluta;
    public double[] frec_acumulada;
    public double[] frec_rel;
    public double[] frec_rel_acumulada;
    public double presion_selectiva;
    public tabla_frecuencias(double[] data) throws Exception{
        ArrayList<Double> dd = new ArrayList<Double>(0);
        for(int i = 0; i < data.length; ++i) dd.add(data[i]);
        double min = utils.my_utils.get_min(dd, Double.POSITIVE_INFINITY);
        min *= -1;

        frec_absoluta = new double[data.length];
        frec_acumulada = new double[data.length];
        frec_rel = new double[data.length];
        frec_rel_acumulada = new double[data.length];

        for (int i = 0 ; i< data.length; i++){
            frec_absoluta[i] = data[i] + min;

            double prev = i == 0 ? 0 : frec_acumulada[i-1];
            frec_acumulada[i] = prev + frec_absoluta[i];
        }
        double best=0;
        double mid=0;
        double total = frec_acumulada[frec_absoluta.length-1];
        for (int i = 0 ; i< data.length; i++){
            mid+= frec_rel[i] = (double) frec_absoluta[i] / total;
            best=Math.max(best,frec_rel[i]);
            frec_rel_acumulada[i] = (double) frec_acumulada[i] / total;
        }
        mid /= data.length;
        presion_selectiva = best/mid;
    }
}
