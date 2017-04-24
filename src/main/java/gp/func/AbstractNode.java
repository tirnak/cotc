package gp.func;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

abstract public class AbstractNode extends GPNode {
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {}

    abstract public String eval();

    protected String evalChild(int indexOfChild) {
        AbstractNode node = (AbstractNode) children[indexOfChild];
        return node.eval();
    }
}
