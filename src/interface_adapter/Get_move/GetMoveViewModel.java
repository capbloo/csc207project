package interface_adapter.Get_move;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetMoveViewModel {
    private GetMoveState state = new GetMoveState();
    public GetMoveViewModel() {}

    public void setState(GetMoveState state) {this.state = state;}

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("GetMove", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public GetMoveState getState() { return state;}
}
