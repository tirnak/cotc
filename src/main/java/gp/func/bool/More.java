package gp.func.bool;

import gp.func.AbstractNode;

public class More extends AbstractNode {
    @Override
    public String toString() {
        return "more";
    }

    @Override
    public String eval() {
        return evalChild(0) + " > " + evalChild(1);
    }

    public int expectedChildren() { return 2; }
}
