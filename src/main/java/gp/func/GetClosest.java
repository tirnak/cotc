package gp.func;

public class GetClosest extends AbstractNode {
    @Override
    public String toString() {
        return "getClosest";
    }

    @Override
    public String eval() {
        return evalChild(0) + ".getClosest(" + evalChild(1) + ")";
    }

    public int expectedChildren() { return 2; }
}
