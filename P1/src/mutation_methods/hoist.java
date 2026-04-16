package mutation_methods;

import codification.IndividualCodification;
import Error.UnreachableCode;
import codification.NodeFactory;
import codification.TreeNode;

//PODA
public class hoist implements BaseMutation{
    public void mutate(IndividualCodification cod) throws Exception{
        TreeNode tn = NodeFactory.choose_random_node(cod.node_tree);
        cod.node_tree = tn;
    }
}