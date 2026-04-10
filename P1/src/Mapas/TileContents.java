package Mapas;

public enum TileContents {
    WALL(0),
    EMPTY(1),
    SAND(10),
    SAMPLE(1);
    public final int weight;
    private TileContents (int w){
        this.weight = w;
    }
}
