package gp.func.print;

import gp.func.AbstractNode;

import java.util.Random;

public class PrintFire extends AbstractNode {
    private static Random r = new Random();

    @Override
    public String toString() {
        return "fire";
    }

    @Override
    public String eval() {
        return "System.out.format(\"FIRE %d %d%n\", x(" +
                evalChild(0) + "), y(" + evalChild(1) + "));\n";
    }

    public int expectedChildren() { return 2; }
}
