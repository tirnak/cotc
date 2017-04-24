package gp.func.math;

import gp.func.AbstractNode;

public class GetY extends AbstractNode {
    @Override
    public String toString() {
        return "getY";
    }

    @Override
    public String eval() {
        return evalChild(0) + ".y";
    }

    public int expectedChildren() { return 1; }
}
