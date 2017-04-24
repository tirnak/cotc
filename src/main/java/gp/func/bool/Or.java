package gp.func.bool;

import gp.func.AbstractNode;

public class Or extends AbstractNode {
    @Override
    public String toString() {
        return "or";
    }

    @Override
    public String eval() {
        return evalChild(0) + " || " + evalChild(1);
    }

    public int expectedChildren() { return 2; }
}
