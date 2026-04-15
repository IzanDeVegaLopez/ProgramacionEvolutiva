package codification;

public class DistObstacleNode extends ConditionalNode<DistObstacleNode>{
    public DistObstacleNode(int n){
        super(new ObstacleDist(n));
    }
}
