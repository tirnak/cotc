package gp.func.math;

import gp.func.AbstractNode;

public class GetDistance extends AbstractNode {
    @Override
    public String toString() {
        return "getDist";
    }

    @Override
    public String eval() {
        return "getDist(" + evalChild(0) + ", " + evalChild(1) + ")";
    }

    public int expectedChildren() { return 2; }
}
