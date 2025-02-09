package fa.dfa;

import fa.State;
import java.util.HashMap;

/**
 * Provides the State infrastructure of a Determinate Finite Automata.
 */
public class DFAState extends State {

    /**
     * A HashMap for storing State's transitions
     */
    protected HashMap<Character, State> transitions = new HashMap<>();

    /**
     * Constuctor that initializes the State's name to the specified name
     *
     * @param name initial name for the State
     */
    public DFAState(String name) {
        super(name);
    }

    /**
     * Adds a transition following a Key:Val format using
     * a character from the DFA's alphabet as the key and
     * a state from the DFA's state set as a value
     *
     * @param alphaChar the character to serve as a key
     * @param state the state to serve as an associated value
     */
    public void transition(char alphaChar, State state) {
        transitions.putIfAbsent(alphaChar, state);
    }
}
