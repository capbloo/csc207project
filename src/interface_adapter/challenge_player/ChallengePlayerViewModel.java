package interface_adapter.challenge_player;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChallengePlayerViewModel extends ViewModel {
    private ChallengePlayerState state = new ChallengePlayerState();

    public ChallengePlayerViewModel(){super("ChallengePlayer");}

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
