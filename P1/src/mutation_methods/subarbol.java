package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;
import codification.NodeFactory;
import codification.TreeNode;

public class subarbol implements BaseMutation{
    public void Mutate(IndividualCodification cod) throws Exception{
        NodeFactory.parent_and_child_node_return node = NodeFactory.choose_random_node_n_parent(cod.node_tree);

        switch(node.option_chosen){
            case NodeFactory.options.stay:{
                cod.impersonate(NodeFactory.create_random_tree(0,5));
                break;
            }
            case NodeFactory.options.go_left:{
                node.parent.L_child = NodeFactory.create_random_tree(node.depth, 5);
                break;
            }
            case NodeFactory.options.go_right:{
                node.parent.R_child = NodeFactory.create_random_tree(node.depth, 5);
                break;
            }
        }
    }
}
