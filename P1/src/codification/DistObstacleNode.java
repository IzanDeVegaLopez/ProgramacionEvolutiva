package codification;

public class DistObstacleNode extends ConditionalNode<DistObstacleNode>{
    public DistObstacleNode(int n){
        super(new ObstacleDist(n));
    }
    public DistObstacleNode(BooleanMethod b){
        super(b);
    }
    @Override
    public DistObstacleNode get_deep_copy() throws Exception {
        DistObstacleNode node = new DistObstacleNode(is_condition_true.get_deep_copy());
        node.L_child = L_child.get_deep_copy();
        node.R_child = R_child.get_deep_copy();
        return node;
    }
    @Override
    public String write_me_down(int depth)throws Exception{
        return utils.my_utils.write_n_tabs(depth)+"if Dist to Obstacle < "+ is_condition_true.value_compared +" do{\n" + L_child.write_me_down(depth+1) + "\n"+utils.my_utils.write_n_tabs(depth)+"} else { \n"+R_child.write_me_down(depth+1)+"\n"+utils.my_utils.write_n_tabs(depth)+"}";
    }
}
