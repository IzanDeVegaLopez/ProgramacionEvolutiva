package mutation_methods;

import codification.BranchNode;
import codification.IndividualCodification;
import Error.UnreachableCode;
import codification.NodeFactory;

public class funcional implements BaseMutation{
    public void mutate(IndividualCodification cod) throws Exception{
        NodeFactory.parent_and_child_node_return node = NodeFactory.choose_random_branch(cod.node_tree);

        if(node.parent == null) return;

        switch(node.option_chosen){
            case stay:{
                throw new UnreachableCode("This should have never happened, non valid enum option chosen in subarbol.mutate, it must always be left or right, we are choosing a path");
            }
            case go_left:{
                BranchNode bn = NodeFactory.create_random_childless_branch_node();
                bn.L_child = ((BranchNode)node.parent.L_child).L_child;
                bn.R_child = ((BranchNode)node.parent.L_child).R_child;
                node.parent.L_child = bn;
                break;
            }
            case go_right:{
                BranchNode bn = NodeFactory.create_random_childless_branch_node();
                bn.L_child = ((BranchNode)node.parent.R_child).L_child;
                bn.R_child = ((BranchNode)node.parent.R_child).R_child;
                node.parent.R_child = bn;
                break;
            }
            default:
                throw new UnreachableCode("This should have never happened, non valid enum option chosen in subarbol.mutate");
        }
    }
}
