package lab4.hr.fer.zemris.ooup.state;

public class StateManager {

    private State currentState;

    public StateManager() {
        this.currentState = new IdleState();
    }

    public StateManager(State currentState) {
        this.currentState = currentState;
    }

    public State setState(State currentState) {
        currentState.onLeaving();
        this.currentState = currentState;
        return currentState;
    }

    public State getCurrentState() {
        return currentState;
    }
}
