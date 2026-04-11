package Mapas.pathing;

import utils.Vector2;

public class AStar_node implements Comparable<AStar_node>{
    public Vector2 current_pos;
    public Vector2 parent_pos;
    //Para el objetivo
    public double estimado = 0;
    //Desde el inicio
    public double real_acumulado = 0;
    //Valor total de estimado + real_acumulado
    public double heurística = Double.POSITIVE_INFINITY;

    public AStar_node(Vector2 current, Vector2 parent, double _estimado, double _acumulado){
        current_pos = current;
        parent_pos = parent;
        estimado = _estimado;
        real_acumulado = _acumulado;
        heurística = estimado + real_acumulado;
    }

    @Override
    public int compareTo(AStar_node other) {
        return Double.compare(this.heurística, other.heurística);
    }

    public AStar_node clone(){
        return new AStar_node(
                this.current_pos == null ? null : this.current_pos.clone(),
                this.parent_pos == null ? null : this.parent_pos.clone(),
                estimado,
                real_acumulado
        );
    }


}
