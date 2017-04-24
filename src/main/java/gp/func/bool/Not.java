package gp.func.bool;

import gp.func.AbstractNode;

public class Not extends AbstractNode {
    @Override
    public String toString() {
        return "not";
    }

    @Override
    public String eval() {
        return "!("+evalChild(0)+")" ;
    }

    public int expectedChildren() { return 1; }
}
