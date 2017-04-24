package gp.func.math;

import gp.func.AbstractNode;

public class Divide extends AbstractNode {
    @Override
    public String toString() {
        return "div";
    }

    @Override
    public String eval() {
        return evalChild(0) + " / (" + evalChild(1) + "+0.0001)";
    }

    public int expectedChildren() { return 2; }
}
