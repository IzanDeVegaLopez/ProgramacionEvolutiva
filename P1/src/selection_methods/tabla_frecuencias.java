package selection_methods;

public class tabla_frecuencias {
    public int[] frec_absoluta;
    public int[] frec_acumulada;
    public float[] frec_rel;
    public float[] frec_rel_acumulada;
    public tabla_frecuencias(int[] data){
        frec_absoluta = new int[data.length];
        frec_acumulada = new int[data.length];
        frec_rel = new float[data.length];
        frec_rel_acumulada = new float[data.length];

        for (int i = 0 ; i< data.length; i++){
            frec_absoluta[i] = data[i];

            int prev = i == 0 ? 0 : frec_acumulada[i-1];
            frec_acumulada[i] = prev + frec_absoluta[i];
        }

        int total = frec_acumulada[frec_absoluta.length-1];
        for (int i = 0 ; i< data.length; i++){
            frec_rel[i] = (float) frec_absoluta[i] / total;

            frec_rel_acumulada[i] = (float) frec_acumulada[i] / total;
        }
    }
}
