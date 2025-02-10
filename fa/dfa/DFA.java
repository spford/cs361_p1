package fa.dfa;

import fa.State;

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
        if ( states.containsKey(name) ) {
            return false;
        }
        states.put(name, new DFAState(name));
        return true;
        //states.putIfAbsent(name, new DFAState(name));
        //return states.get(name).toString().equals(name);
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
        DFAState currentState = states.get(startState);
        for (char c : s.toCharArray()) {    //For each Character
            if (!sigma.contains(c)) {       //Is Character part of the Language
                return false;
            } else {
                if ( currentState.transitions.containsKey(c) ) {
                    currentState = (DFAState) currentState.transitions.get(c);
                }
            }
        }
        if (finalStates.contains(currentState.getName())) {
            return true;
        } else {
            return false;
        }
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
        String returnString = "Q = { " + String.join(" ", states.keySet()) + " }\n";
        returnString += "Sigma = { ";
        for (Character chr : getSigma()) {
            returnString += chr + " ";
        }
        returnString += "}\n";
        returnString += "delta =\n\t";
        for (Character chr : getSigma()) {
            returnString += chr + " ";
        }
        returnString += "\n";
        for (DFAState state : states.values()) {
            returnString += state.toString() + "\t";
            for ( char chr : getSigma() ) {
                returnString += state.transitions.get(chr).getName() + " ";
            }
            returnString += "\n";
        }
        returnString += "q0 = " + startState + "\n";
        returnString += "F = { " + String.join(" ", finalStates) + " }\n";
        return returnString;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if ( !states.containsKey(fromState) || !states.containsKey(toState) || !sigma.contains(onSymb) ) {
            return false;
        }
        states.get(fromState).transition(onSymb, states.get(toState));
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // ToDo need to finish swap logic
        DFA dfaNew = new DFA();

        for (String name : this.states.keySet()) {
            dfaNew.addState(name);
        }

        for (Character chr : this.getSigma()) {
            dfaNew.addSigma(chr);
        }

        finalStates.forEach(dfaNew::setFinal);

        if ( !sigma.contains(symb1) && !sigma.contains(symb2) ) { return null; }

        for ( DFAState state : dfaNew.states.values() ) {
            state.transitions.replace(symb1, states.get(symb2));
            state.transitions.replace(symb2, states.get(symb1));
        }
        return dfaNew;
    }

    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.addSigma('0');
        dfa.addSigma('1');

        dfa.getSigma();

        dfa.addState("a");
        dfa.addState("b");

        dfa.addState("a");
        dfa.setStart("a");
        dfa.setFinal("b");

        dfa.addTransition("a", "a", '0');
        dfa.addTransition("a", "b", '1');
        dfa.addTransition("b", "a", '0');
        dfa.addTransition("b", "b", '1');

        dfa.addTransition("c", "b", '1');

        System.out.println(dfa.toString());
        System.out.println("Development Test: ");


    }
}