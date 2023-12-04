package interface_adapter.HighlightSquare;

import interface_adapter.ViewModel;
import interface_adapter.make_move.MakeMoveState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HighlightViewModel extends ViewModel {
    public HighlightViewModel() {
        super("HighlightSquare");
    }
    private HighlightState state = new HighlightState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public HighlightState getState() {
        return state;
    }
}
