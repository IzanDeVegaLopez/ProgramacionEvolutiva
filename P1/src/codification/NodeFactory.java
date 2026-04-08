package codification;

import Error.UnreachableCode;

public class NodeFactory {
    final static int max_depth = 3;

    public static TreeNode create_random_node_tree(int current_depth) throws Exception{
        //NEED A LEAF NODE
        if(max_depth==current_depth){
            return create_random_leaf_node();
        }

        //Select between the different types of branch node and a leaf node
        int random_idx = utils.my_utils.
                get_random(1+branch_node_types.BNT_COUNT.ordinal());
        //Leaf node was chosen
        if(random_idx == branch_node_types.BNT_COUNT.ordinal()){
            return create_random_leaf_node();
        }
        //Branch node was chosen
        BranchNode created_branch_node = create_random_childless_branch_node();
        //Create the branch node childs
        created_branch_node.L_child = create_random_node_tree(current_depth+1);
        created_branch_node.R_child = create_random_node_tree(current_depth+1);
        return created_branch_node;
    }

    private static LeafNode create_random_leaf_node() throws Exception{
        int random_idx = utils.my_utils.
                get_random(leaf_node_types.LNT_COUNT.ordinal());
        return create_leaf_node(leaf_node_types.values()[random_idx]);
    }
    //This may create a branch node or a leaf node
    private static BranchNode create_random_childless_branch_node() throws Exception{
        int random_idx = utils.my_utils.
                get_random(branch_node_types.BNT_COUNT.ordinal());
        return create_childless_branch_node(branch_node_types.values()[random_idx]);
    }

    //ANT stands for Action Node Types
    public enum leaf_node_types{
        LNT_ADVANCE,
        LNT_TURN_LEFT,
        LNT_TURN_RIGHT,
        LNT_COUNT
    };
    public enum branch_node_types{
        BNT_SEQUENCE_NODE,
        BNT_COUNT
    };
    public static TreeNode create_node(leaf_node_types ant) throws Exception{
        return create_leaf_node(ant);
    }
    public static TreeNode create_node(branch_node_types cnt) throws Exception{
        return create_childless_branch_node(cnt);
    }

    public static LeafNode create_leaf_node(leaf_node_types leaf_node_type) throws Exception{
        return switch (leaf_node_type) {
            case LNT_ADVANCE -> new AdvanceNode();
            case LNT_TURN_LEFT -> new TurnLeftNode();
            case LNT_TURN_RIGHT -> new TurnRightNode();
            default -> throw new UnreachableCode("leaf node type was count, that should have never happened");
        };
    }

    public static BranchNode create_childless_branch_node(branch_node_types cnt) throws Exception{
        return switch(cnt){
            case BNT_SEQUENCE_NODE -> new SequenceNode();
            default -> throw new UnreachableCode("branch node type was count, that should have never happened");
        };
    }


    enum options{
        stay, go_left, go_right, count
    }

    public static TreeNode choose_random_node(TreeNode t_node) throws Exception{
        TreeNode aux = t_node;
        while(!aux.is_leaf()){
            int random_idx = utils.my_utils.get_random(options.count.ordinal());
            options opt = options.values()[random_idx];
            switch(opt){
                case options.go_left :
                    aux = ((BranchNode)aux).L_child;
                break;
                case options.go_right :
                    aux = ((BranchNode)aux).R_child;
                break;
                case options.stay :
                    return aux;
                default:
                    throw new UnreachableCode("Count is not a real option, this should not have been chosen");
            }
        }
        return aux;
    }
}
