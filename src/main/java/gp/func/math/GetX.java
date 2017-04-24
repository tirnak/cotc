package gp.func.math;

import gp.func.AbstractNode;

public class GetX extends AbstractNode {
    @Override
    public String toString() {
        return "getX";
    }

    @Override
    public String eval() {
        return evalChild(0) + ".x";
    }

    public int expectedChildren() { return 1; }
}
