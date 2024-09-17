package fa.nfa;

import fa.State;

import java.util.LinkedHashSet;
import java.util.Set;

public class NFA implements NFAInterface {

    LinkedHashSet<Character> alphabet ;
    LinkedHashSet<NFAState> states;
    NFAState startState;
    LinkedHashSet<NFAState> finalStates;

    public NFA(){
        alphabet = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        finalStates = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (getStateByName(name, states) != null) {
            return false; // State already exists
        }
        NFAState newState = new NFAState(name);
        states.add(newState);
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        NFAState state = getStateByName(name, states);
        if (state == null) {
            return false; // State does not exist
        }
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = getStateByName(name, states);
        if (state == null) {
            return false; // State does not exist
        }
        startState = state;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        NFAState currentState = startState;
        for (char c : s.toCharArray()) {
            currentState = currentState.getToState(c); // Get the next state based on the input symbol
            if (currentState == null) {
                return false; // No valid transition
            }
        }
        return finalStates.contains(currentState); // Check if the final state is accepting
    }

    @Override
    public Set<Character> getSigma() {
        return alphabet;
    }

    @Override
    public State getState(String name) {
        return getStateByName(name, states);
    }

    @Override
    public boolean isFinal(String name) {
        return (getStateByName(name, finalStates) != null);
    }

    @Override
    public boolean isStart(String name) {
        return startState.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        //validate parameters
        if(getState(fromState) == null || getState(toState) == null) return false;
        if(!alphabet.contains(onSymb)) return false;

        getStateByName(fromState, states).addTransition(getStateByName(toState, states), onSymb);
        return true;
    }

    @Override
    public NFA swap(char symb1, char symb2) {
        //initialize new NFA
        NFA copyNFA = new NFA();
        for(char c : alphabet){
            copyNFA.addSigma(c);
        }
        for(NFAState state : states){
            copyNFA.addState(state.getName());
        }
        copyNFA.setStart(startState.getName());
        for(NFAState state : finalStates) {
            copyNFA.setFinal(state.getName());
        }

        for(NFAState currState : states){
            NFAState symb1State = currState.transitions.get(symb1);
            NFAState symb2State = currState.transitions.get(symb2);
            copyNFA.addTransition(currState.getName(), symb1State.getName(), symb2);
            copyNFA.addTransition(currState.getName(), symb2State.getName(), symb1);
        }

        return copyNFA;
    }

    /**
     * A private getter for a specific state from the specified set to help with getting DFAStates
     * @author Austin Hunt
     * @param target the name of the target state
     * @param stateSet the set of states to get target from
     * @return the state associated with the name, or null of it isn't in the set
     */
    private NFAState getStateByName(String target, LinkedHashSet<NFAState> stateSet) {
        for (NFAState curr : stateSet) {
            if (curr.getName().equals(target)) {
                return curr;
            }
        }
        return null;
    }

    @Override
    public String toString() { //I know, it's pretty ugly -Austin
        Object[] stateArr = states.toArray();
        Object[] alphabetArr = alphabet.toArray();
        Object[] finalStateArr = finalStates.toArray();

        StringBuilder finalString = new StringBuilder(" Q = { ");
        for(Object state : stateArr){
            finalString.append(state.toString());
            finalString.append(' ');
        }
        finalString.append("}\nSigma = { ");

        for(Object symb : alphabetArr){
            finalString.append(symb.toString());
            finalString.append(' ');
        }
        finalString.append("}\ndelta =\n		");

        for(Object symb : alphabetArr){
            finalString.append(symb.toString());
            finalString.append('\t');
        }
        finalString.deleteCharAt(finalString.lastIndexOf("\t"));
        finalString.append("\n\t");

        for(Object state : stateArr){
            finalString.append(state.toString()).append("\t");
            for(Object symb : alphabetArr){
                finalString.append( ((NFAState) state).getToState((char)symb)).append('\t');
            }
            finalString.append("\n\t");
        }
        finalString.deleteCharAt(finalString.lastIndexOf("\t"));

        finalString.append("q0 = ").append(startState.getName()).append('\n');

        finalString.append("F = { ");
        for(Object state : finalStateArr){
            finalString.append(state.toString());
        }
        finalString.append(" }");

        return finalString.toString();
    }
}
