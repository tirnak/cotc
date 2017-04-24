package gp.func.bool;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import gp.func.AbstractNode;

public class Ifelse extends AbstractNode {

    public String toString() { return "ifelse"; }

    public String eval() {
        return "if (" + evalChild(0) + ") {\n" +
                    evalChild(1) + "\n" +
                " } else { \n" +
                    evalChild(2) + "\n" +
                " } \n";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {}

    public int expectedChildren() { return 3; }

}



