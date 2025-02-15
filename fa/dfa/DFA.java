package fa.dfa;

import fa.State;

import java.util.*;


/**
 * DFA is an implementation that provides all necessary operations to construct
 * a Determinate Finite Automata.
 * @author Luis Acosta
 * @author Spencer Ford
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

    /** Travels the DFA object and return if the passed string is part of the language.
     * This method will break the string up in to an array of Characters to be compared
     * to the Characters that make up the names of the states of this DFA object. For every
     * character of the array this method will see if the state has a valid transition available.
     * The method will consume the string as long as valid transitions exist until the end when it
     * will make sure the state that it ends on is a final state.
     * @param s - the string that is being tested if it is accepted by the DFA object
     * @return boolean - true - specified string successfully traveled the DFA and landed on a final state
     *                 - false - specified string failed to find valid transitions to consume the input string
     *                           or the current state at the end of the input string is not a final state
     **/
    @Override
    public boolean accepts(String s) {
        DFAState currentState = states.get(startState); //Create a new DFAState object to hold current state
        for (char c : s.toCharArray()) {                //Break up input string into Character array
            if (!sigma.contains(c)) { return false; }   //Check that the current Character is in the language
            String nextState = currentState.transitions.get(c).getName();   //Grab the valid transition
            if (nextState == null) { return false; }    //Checks if there is a valid transition

            currentState = states.get(nextState);       //Set currentState to state specified by valid transition
            }
        return finalStates.contains(currentState.getName()); //Checks currentState is in the final state set
    }

    /** Add a Character to the language
     * @param symbol - Character to be added to the language
     **/
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);  //LinkedHashSets only adds unique symbols
    }

    /** Returns Set of Characters containing the language
     * @return sigma - Set of Characters containing the language
     **/
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    /** Returns the State object requested
     * @param name - the name of the state to be returned
     * @return State object
     **/
    public State getState(String name) {
        return states.get(name);
    }

    /** Returns boolean value if specified string is a final state
     * @param name - the name of the state to be checked for final state status
     * @return boolean - true - state is a final state
     *                  false - state is not a final state
     **/
    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

    /** Returns boolean value if specified string is the start state
     * @param name - the name of the state to be checked for start state status
     * @return boolean - true - state is the start state
     *                  false - state is not the start state
     **/
    @Override
    public boolean isStart(String name) {
        return startState.equals(name);
    }

    /** Returns string of 5-tuple DFA definition
     * @return string
     **/
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

    /** Returns boolean value if specified transition is valid
     * This method will check to make sure fromState, toState are states already in the DFA and the character
     * is in the language. Then it will add the transition to the fromState transition set.
     * @param fromState - state transiton is associated with
     *          toState - state transition is going to
     *           onSymb - character in the language the transition occurs on
     * @return boolean - true - transition successfully added
     *                  false - transition failed to be added
     **/
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {   //Checks params are valid
        if ( !states.containsKey(fromState) || !states.containsKey(toState) || !sigma.contains(onSymb)) {
            return false;
        }
        states.get(fromState).transitions.put(onSymb, states.get(toState)); //Add valid transition to fromState
                                                                            // transition set
        return true;
    }

    /** Returns this DFA object with specified transition swaps
     *
     * @param   symb1 - character swap
     *          sybm2 - character swap
     * @return DFA -
     **/
    @Override
    public DFA swap(char symb1, char symb2) {
        if (sigma.contains(symb1) && sigma.contains(symb2)) {               //Checks both symbols exist in the language
            DFA DFACopy = new DFA(sigma, states, finalStates, startState);  //Creates new DFA object copy of this DFA
            for (DFAState stateCopy : DFACopy.states.values()) {            //Iterates over every state in DFA
                String toState = stateCopy.transitions.remove(symb1).toString();    //Remove old transition on symb1 and save
                String fromState = stateCopy.transitions.remove(symb2).toString();  //Remove old transition on symb2 and save
                DFACopy.addTransition(stateCopy.toString(), toState, symb2);        //Add new transition to toState on symb2
                DFACopy.addTransition(stateCopy.toString(), fromState, symb1);      //Add new transition to fromState on symb1
            }
            return DFACopy;
        }
        return null;
    }
}


