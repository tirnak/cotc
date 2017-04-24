package gp.func.math;

import gp.func.AbstractNode;

public class Sum extends AbstractNode {
    @Override
    public String toString() {
        return "plus";
    }

    @Override
    public String eval() {
        return evalChild(0) + " + " + evalChild(1) ;
    }

    public int expectedChildren() { return 2; }
}
