package gp.func.bool;

import gp.func.AbstractNode;

public class And extends AbstractNode {

    @Override
    public String eval() {
        return evalChild(0) + " && " + evalChild(1);
    }

    @Override
    public String toString() {
        return "and";
    }

    public int expectedChildren() { return 2; }
}
