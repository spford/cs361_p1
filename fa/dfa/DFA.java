package fa.dfa;

import fa.State;
import fa.dfa.DFAInterface;
import fa.dfa.DFAState;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

/**
 * DFA is an implementation that provides all necessary operations to construct
 * a Determinate Finite Automata.
 */
public class DFA implements DFAInterface {
    protected LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    protected HashMap<String, DFAState> states = new HashMap<>();
    protected TreeSet<String> finalStates = new TreeSet<String>();
    protected String startState;

    public DFA() {

    }

    @Override
    public boolean addState(String name) {
        states.putIfAbsent(name, new DFAState(name));
        return states.get(name).toString().equals(name);
    }

    @Override
    public boolean setFinal(String name) {
        if (states.containsKey(name)) {
            finalStates.add(name);
            return true;
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        if (states.containsKey(name)) {
            startState = name;
            return true;
        }
        return false;
    }

    @Override
    public boolean accepts(String s) {
        /* TO - DO */
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    public Set<Character> getSigma() {
        return sigma;
    }

    public State getState(String name) {
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

    @Override
    public boolean isStart(String name) {
        return startState.equals(name);
    }

    @Override
    public String toString() {
        String returnString = "Q = {" + String.join(" ", states.keySet()) + "}\n";
        returnString += "Sigma = { ";
        for (Character chr : getSigma()) {
            returnString += chr + " ";
        }
        returnString += "}\n";
        returnString += "delta\n";
        returnString += "startState = " + startState + "\n";
        returnString += "finalStates = {" + String.join(" ", finalStates) + "}\n";
        return returnString;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        /* TO - DO */
        return false;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        /* TO - DO */
        return null;
    }

    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.addSigma('1');
        dfa.addSigma('0');

        dfa.getSigma();

        dfa.addState("a");
        dfa.addState("b");

        dfa.setStart("a");
        dfa.setFinal("b");

        System.out.println(dfa.toString());
        System.out.println();
    }
}


