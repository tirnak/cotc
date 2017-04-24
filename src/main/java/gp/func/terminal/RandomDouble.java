package gp.func.terminal;

import gp.func.AbstractNode;

import java.util.Random;

public class RandomDouble extends AbstractNode {
    private static Random r = new Random();
//    private float f = r.nextFloat();

    @Override
    public String toString() {
        return "randomDouble";
    }

    @Override
    public String eval() {
        return String.format("%3.3f", r.nextGaussian()) ;
    }
}
