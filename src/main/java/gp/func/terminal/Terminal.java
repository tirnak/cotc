package gp.func.terminal;

import gp.func.AbstractNode;

abstract public class Terminal extends AbstractNode {

    @Override
    public String eval() {
        return this.toString();
    }

    public int expectedChildren() { return 0; }
}
