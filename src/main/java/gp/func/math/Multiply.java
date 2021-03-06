package gp.func.math;

import gp.func.AbstractNode;

public class Multiply extends AbstractNode {
    @Override
    public String toString() {
        return "mult";
    }

    @Override
    public String eval() {
        return evalChild(0) + " * " + evalChild(1);
    }

    public int expectedChildren() { return 2; }
}
