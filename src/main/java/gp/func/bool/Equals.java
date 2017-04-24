package gp.func.bool;

import gp.func.AbstractNode;

public class Equals extends AbstractNode {
    @Override
    public String toString() {
        return "equals";
    }

    @Override
    public String eval() {
        return "equals(" + evalChild(0) + ", " + evalChild(1) + ")";
    }

    public int expectedChildren() { return 2; }
}
