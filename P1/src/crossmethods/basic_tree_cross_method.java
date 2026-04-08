package crossmethods;

import codification.IndividualCodification;

public class basic_tree_cross_method implements cross_method{

    @Override
    public void cross(IndividualCodification L, IndividualCodification R) throws Exception{
        IndividualCodification l = new IndividualCodification(), r = new IndividualCodification();
        l.impersonate(L);
        r.impersonate(R);
    }
}
