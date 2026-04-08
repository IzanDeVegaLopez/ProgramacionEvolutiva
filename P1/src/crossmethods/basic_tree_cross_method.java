package crossmethods;

import codification.IndividualCodification;
import codification.NodeFactory;
import codification.TreeNode;

public class basic_tree_cross_method implements cross_method{

    @Override
    public void cross(IndividualCodification L, IndividualCodification R) throws Exception{
        IndividualCodification l = new IndividualCodification(), r = new IndividualCodification();
        l.impersonate(L);
        r.impersonate(R);

        TreeNode nodeR = NodeFactory.choose_random_node(r.node_tree);
        TreeNode nodeL = NodeFactory.choose_random_node(l.node_tree);
        //Store copy of branch to do the swap
        TreeNode nodeL_copy = nodeL.get_deep_copy();

        //This should change both nodes to the other position in its tree
        nodeL = nodeR.get_deep_copy();
        nodeR = nodeL_copy;
    }
}
