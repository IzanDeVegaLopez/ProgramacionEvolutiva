package crossmethods;

import codification.codificacion_binaria;

public interface bin_cross_base {
    public void cross(codificacion_binaria cod, int elemIdxA, int elemIdxB);
    public void crossAll(codificacion_binaria[] cods, int codIdxA, int codIdxB);
}