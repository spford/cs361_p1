package fa.dfa;

import fa.State;

import java.util.*;


/**
 * DFA is an implementation that provides all necessary operations to construct
 * a Determinate Finite Automata.
 */
public class DFA implements DFAInterface {
    protected LinkedHashSet<Character> sigma = new LinkedHashSet<>();
    protected HashMap<String, DFAState> states = new LinkedHashMap<>();
    protected TreeSet<String> finalStates = new TreeSet<>();
    protected String startState;

    public DFA() {

    }

    public DFA(Collection<Character> sigma, HashMap<String, DFAState> states, SortedSet<String> finalStates, String startState) {
        this.sigma = new LinkedHashSet<>(sigma);
        for (Map.Entry<String, DFAState> entryState : states.entrySet()) {
            DFAState deepCopyState = new DFAState(entryState.getKey());
            for (Map.Entry<Character, State> entryTransition : entryState.getValue().transitions.entrySet()) {
                State transitionState = entryTransition.getValue();
                deepCopyState.transitions.put(entryTransition.getKey(), transitionState);
            }

            this.states.put(entryState.getKey(), deepCopyState);
        }
        this.finalStates = new TreeSet<>(finalStates);
        this.startState = startState;
    }

    /** Adds States to a DFA Instance
     * @param name - the requested name of the state to be added.
     * @return boolean - true - state does not already exist and was successfully added
     *                 - false - state was not added. Probably do to the state of 'name' already exists.
     **/
    @Override
    public boolean addState(String name) {
        if (states.containsKey(name)) { //Check the state is not already in the state set
            return false;
        }
        states.put(name, new DFAState(name)); //Add new DFAState object to state set
        return true;
    }


    /** Adds existing state to a final state set
     * @param name - the name of the state to be added to final state set
     * @return boolean - true - specified state was successfully added to final state set
     *                 - false - specified state not added to final state set
     **/
    @Override
    public boolean setFinal(String name) {
        if (states.containsKey(name)) { //Check the state is already in the state set
            finalStates.add(name);      //Adds the valid state to the final state set
            return true;
        }
        return false;
    }

    /** Sets existing state as the start state
     * @param name - the name of the state to be made the start state
     * @return boolean - true - specified state was successfully made the start state
     *                 - false - specified state not made the start state
     **/
    @Override
    public boolean setStart(String name) {
        if (states.containsKey(name)) { //Check the state is already in the state set
            startState = name;          //Makes valid state the start state
            return true;
        }
        return false;
    }

    /** Adds existing state to a final state set
     * @param name - the name of the state to be added to final state set
     * @return boolean - true - specified state was successfully added to final state set
     *                 - false - specified state not added to final state set
     **/
    @Override
    public boolean accepts(String s) {
        DFAState currentState = states.get(startState);
        for (char c : s.toCharArray()) {
            if (!sigma.contains(c)) { return false; }
            String nextState = currentState.transitions.get(c).getName();
            if (nextState == null) { return false; }

            currentState = states.get(nextState);
            }
        return finalStates.contains(currentState.getName());
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
            for( char chr : getSigma() ) {
                returnString += state.transitions.get(chr).toString() + " ";
            }
            returnString += "\n";
        }

        returnString += "q0 = " + startState + "\n";
        returnString += "F = { " + String.join(" ", finalStates.descendingSet()) + " }\n";
        return returnString;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if ( !states.containsKey(fromState) || !states.containsKey(toState) || !sigma.contains(onSymb)) {
            return false;
        }
        states.get(fromState).transitions.put(onSymb, states.get(toState));
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        if (sigma.contains(symb1) && sigma.contains(symb2)) {
            DFA DFACopy = new DFA(sigma, states, finalStates, startState);
            for (DFAState stateCopy : DFACopy.states.values()) {
                String toState = stateCopy.transitions.remove(symb1).toString();
                String fromState = stateCopy.transitions.remove(symb2).toString();
                DFACopy.addTransition(stateCopy.toString(), toState, symb2);
                DFACopy.addTransition(stateCopy.toString(), fromState, symb1);
            }
            return DFACopy;
        }
        return null;
    }

    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.addSigma('2');
        dfa.addSigma('1');

        dfa.addState("G");
        dfa.addState("D");

        dfa.setFinal("G");
        dfa.setFinal("D");

        dfa.addState("A");
        dfa.setStart("D");
        dfa.setStart("A");

        dfa.addState("B");
        dfa.addState("C");
        dfa.addState("E");
        dfa.addState("F");

        dfa.addState("A");
        dfa.setFinal("K");
        dfa.setStart("BK");

        dfa.addTransition("A", "B", '1');
        dfa.addTransition("A", "C", '2');

        dfa.addTransition("B", "D", '1');
        dfa.addTransition("B", "E", '2');

        dfa.addTransition("C", "F", '1');
        dfa.addTransition("C", "G", '2');

        dfa.addTransition("C", "F", '1');
        dfa.addTransition("C", "G", '2');

        dfa.addTransition("D", "D", '1');
        dfa.addTransition("D", "E", '2');

        dfa.addTransition("E", "D", '1');
        dfa.addTransition("E", "E", '2');

        dfa.addTransition("F", "F", '1');
        dfa.addTransition("F", "G", '2');

        dfa.addTransition("G", "F", '1');
        dfa.addTransition("G", "G", '2');


        dfa.addTransition("FF", "F", '1');
        dfa.addTransition("F", "GG", '2');

        dfa.addTransition("G", "F", 'K');
        dfa.addTransition("A", "K", '7');

        System.out.println(dfa.toString());

        DFA dfaCopy = new DFA(dfa.sigma, dfa.states, dfa.finalStates, dfa.startState);
        dfaCopy = dfaCopy.swap('1', '2');

        System.out.println(dfa.toString());
        System.out.println(dfaCopy.toString());
    }
}


