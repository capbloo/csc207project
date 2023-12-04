package interface_adapter.challenge_player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChallengePlayerViewModel {
    private ChallengePlayerState state = new ChallengePlayerState();

    public ChallengePlayerViewModel(){}

    public ChallengePlayerState getState() {
        return state;
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("ChallengePlayer", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
