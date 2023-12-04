package interface_adapter.CheckGameEnds;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CheckGameEndsViewModel {

    private CheckGameEndsState state = new CheckGameEndsState();
    public CheckGameEndsViewModel() {}

    public void setState(CheckGameEndsState state){this.state = state;}

    public CheckGameEndsState getState(){return state;}

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("CheckGameEnds", null, this.state);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
