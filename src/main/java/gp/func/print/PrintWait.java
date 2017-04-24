package gp.func.print;

import gp.func.AbstractNode;

import java.util.Random;

public class PrintWait extends AbstractNode {
    private static Random r = new Random();

    @Override
    public String toString() {
        return "wait";
    }

    @Override
    public String eval() {
        return "System.out.format(\"WAIT%n\");\n";
    }
}
