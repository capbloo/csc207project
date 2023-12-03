package interface_adapter.Get_move;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetMoveViewModel extends ViewModel {
    private GetMoveState state = new GetMoveState();
    public GetMoveViewModel() {super("GetMove");}

    public void setState(GetMoveState state) {this.state = state;}

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("GetMove", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public GetMoveState getState() { return state;}
}
