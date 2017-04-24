package gp.func.bool;

import gp.func.AbstractNode;

public class Less extends AbstractNode {
    @Override
    public String toString() {
        return "less";
    }

    @Override
    public String eval() {
        return evalChild(0) + " < " + evalChild(1);
    }

    public int expectedChildren() { return 2; }
}
