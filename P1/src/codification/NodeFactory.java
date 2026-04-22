package codification;

import Error.UnreachableCode;
import utils.my_utils;

public class NodeFactory {
    final static float leaf_node_probability = 0.7f;
    public static TreeNode create_random_grow_tree(int current_depth, int max_depth) throws Exception{
        //NEED A LEAF NODE
        if(max_depth==current_depth){
            return create_random_leaf_node_advance_double_prob();
        }

        //Select between the different types of branch node and a leaf node
        boolean use_terminal_node = Math.random() <= leaf_node_probability;
        //Leaf node was chosen
        if(use_terminal_node){
            return create_random_leaf_node_advance_double_prob();
        }
        //Branch node was chosen
        BranchNode created_branch_node = create_random_childless_branch_node();
        //Create the branch node childs
        created_branch_node.L_child = create_random_grow_tree(current_depth+1, max_depth);
        created_branch_node.R_child = create_random_grow_tree(current_depth+1, max_depth);
        return created_branch_node;
    }
    public static TreeNode create_random_complete_tree(int current_depth, int max_depth) throws Exception{
        //NEED A LEAF NODE
        if(max_depth<=current_depth){
            return create_random_leaf_node_advance_double_prob();
        }

        //Branch node was chosen
        BranchNode created_branch_node = create_random_childless_branch_node();
        //Create the branch node childs
        created_branch_node.L_child = create_random_complete_tree(current_depth+1, max_depth);
        created_branch_node.R_child = create_random_complete_tree(current_depth+1, max_depth);
        return created_branch_node;
    }
    public static TreeNode create_random_tree(int current_depth, int max_depth) throws Exception{
        //NEED A LEAF NODE
        if(max_depth==current_depth){
            return create_random_leaf_node();
        }

        boolean use_terminal_node = Math.random() <= 0.5;
        if(use_terminal_node){
            return create_random_leaf_node();
        }

        //Branch node was chosen
        BranchNode created_branch_node = create_random_childless_branch_node();

        //Create the branch node childs
        created_branch_node.L_child = create_random_complete_tree(current_depth+1, max_depth);
        created_branch_node.R_child = create_random_complete_tree(current_depth+1, max_depth);
        return created_branch_node;
    }

    public static LeafNode create_random_leaf_node() throws Exception{
        int random_idx = utils.my_utils.
                get_random(leaf_node_types.LNT_COUNT.ordinal());
        return create_leaf_node(leaf_node_types.values()[random_idx]);
    }
    public static LeafNode create_random_leaf_node_advance_double_prob() throws Exception{
        int random_idx = utils.my_utils.
                get_random(leaf_node_types.LNT_COUNT.ordinal()+1);
        if(leaf_node_types.LNT_COUNT.ordinal()==random_idx) return create_leaf_node(leaf_node_types.LNT_ADVANCE);
        return create_leaf_node(leaf_node_types.values()[random_idx]);
    }
    //This may create a branch node or a leaf node
    public static BranchNode create_random_childless_branch_node() throws Exception{
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
        BNT_SAND_NODE,
        BNT_OBSTACLE_NODE,
        BNT_SAMPLE_NODE,
        BNT_ENERGY_LEVEL_CHECK_NODE,
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

    public static final int max_random_value_for_numeric_condition_values = 100;
    public static BranchNode create_childless_branch_node(branch_node_types cnt) throws Exception{
        int random = my_utils.get_random(max_random_value_for_numeric_condition_values);
        return switch(cnt){
            case BNT_SEQUENCE_NODE -> new SequenceNode();
            case BNT_SAMPLE_NODE -> new DistMuestraNode(my_utils.get_random(10));
            case BNT_SAND_NODE -> new DistSandNode(my_utils.get_random(10));
            case BNT_OBSTACLE_NODE -> new DistObstacleNode(my_utils.get_random(10));
            case BNT_ENERGY_LEVEL_CHECK_NODE -> new EnergyLevelCheckNode(my_utils.get_random(100));
            default -> throw new UnreachableCode("branch node type was count, that should have never happened");
        };
    }


    public enum options{
        go_left, go_right, stay, count
    }

    public static TreeNode choose_random_node(TreeNode t_node) throws Exception{
        if(t_node == null) throw new UnreachableCode("Node was null in call to choose_random_node");
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
            if(aux==null) throw new UnreachableCode("aux was null in iteration in call to choose_random_node");
        }
        return aux;
    }
    public static class parent_and_child_node_return{
        public BranchNode parent;
        public options option_chosen;
        public int depth;
        public parent_and_child_node_return(BranchNode _parent, options _option, int _depth){
            parent = _parent;
            option_chosen = _option;
            depth = _depth;
        }
    }
    public static parent_and_child_node_return choose_random_node_n_parent(TreeNode t_node) throws Exception{
        TreeNode aux = t_node;
        options last_opt = options.stay;
        BranchNode parent = null;
        int depth = 0;
        while(!aux.is_leaf()){
            int random_idx = utils.my_utils.get_random(options.count.ordinal());
            options opt = options.values()[random_idx];
            switch(opt){
                case options.go_left :
                    parent = ((BranchNode)aux);
                    aux = parent.L_child;
                    break;
                case options.go_right :
                    parent = ((BranchNode)aux);
                    aux = parent.R_child;
                    break;
                case options.stay :
                    return new parent_and_child_node_return(parent, last_opt, depth);
                default:
                    throw new UnreachableCode("Count is not a real option, this should not have been chosen");
            }
            ++depth;
        }
        return new parent_and_child_node_return(parent, last_opt, depth);
    }
    public static parent_and_child_node_return choose_random_leaf(TreeNode t_node) throws Exception{
        TreeNode aux = t_node;
        int depth = 0;
        while(!aux.is_leaf()){
            int random_idx = utils.my_utils.get_random(options.stay.ordinal());
            options opt = options.values()[random_idx];
            switch(opt){
                case options.go_left : {
                    BranchNode bn = ((BranchNode) aux);
                    if (bn.L_child.is_leaf())
                        return new parent_and_child_node_return(bn, options.go_left, depth);
                    aux = bn.L_child;
                    break;
                }
                case options.go_right : {
                    BranchNode bn = ((BranchNode) aux);
                    if (bn.R_child.is_leaf())
                        return new parent_and_child_node_return(bn, options.go_right, depth);
                    aux = bn.R_child;
                    break;
                }
                default:
                    throw new UnreachableCode("Count is not a real option, this should not have been chosen");
            }
            ++depth;
        }
        return new parent_and_child_node_return(null, options.stay, depth);
    }

    public static parent_and_child_node_return choose_random_branch(TreeNode t_node) throws Exception{
        TreeNode aux0 = t_node;
        int depth = 0;
        if(aux0.is_leaf()) return new parent_and_child_node_return(null, options.stay, depth);

        BranchNode parent = null;
        BranchNode aux = (BranchNode)aux0;
        options last_opt = options.stay;
        while(!aux.L_child.is_leaf() && !aux.R_child.is_leaf()){
            int random_idx = utils.my_utils.get_random(options.stay.ordinal());
            options opt = options.values()[random_idx];
            switch(opt){
                case options.stay: {
                    return new parent_and_child_node_return(parent, last_opt, depth);
                }
                case options.go_left : {
                    parent = aux;
                    aux = (BranchNode)aux.L_child;
                    break;
                }
                case options.go_right : {
                    parent = aux;
                    aux = (BranchNode)aux.R_child;
                    break;
                }
                default:
                    throw new UnreachableCode("Count is not a real option, this should not have been chosen");
            }
            last_opt = opt;
            ++depth;
        }
        return new parent_and_child_node_return(parent, last_opt, depth);
    }
}
