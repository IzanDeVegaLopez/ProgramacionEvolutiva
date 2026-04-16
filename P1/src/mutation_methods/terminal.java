package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;
import codification.NodeFactory;

import static codification.NodeFactory.choose_random_leaf;

public class terminal implements BaseMutation{
    public void mutate(IndividualCodification cod) throws Exception{
        NodeFactory.parent_and_child_node_return node = NodeFactory.choose_random_leaf(cod.node_tree);
        switch(node.option_chosen){
            case NodeFactory.options.stay:{
                cod.impersonate(NodeFactory.create_random_leaf_node());
                break;
            }
            case NodeFactory.options.go_left:{
                node.parent.L_child = NodeFactory.create_random_leaf_node();
                break;
            }
            case NodeFactory.options.go_right:{
                node.parent.R_child = NodeFactory.create_random_leaf_node();
                break;
            }
            default:
                throw new UnreachableCode("This should have never happened, non valid enum option chosen in subarbol.mutate");
        }
    }
}