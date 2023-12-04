package interface_adapter.challenge_ai;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChallengeAIViewModel{
    private ChallengeAIState state = new ChallengeAIState();

    public ChallengeAIViewModel() {}

    public ChallengeAIState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("ChallengeAI", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
