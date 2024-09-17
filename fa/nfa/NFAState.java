package fa.nfa;

import fa.State;

import java.util.HashMap;

public class NFAState extends State {

    HashMap<Character, NFAState> transitions = new HashMap<>();

    public NFAState(String name){
        super(name);
    }

    public void addTransition(NFAState toState, char symbol){
        transitions.put(symbol, toState);
    }

    public NFAState getToState(char symbol) {
        return transitions.get(symbol); // Get the next state for the input symbol
    }

}
