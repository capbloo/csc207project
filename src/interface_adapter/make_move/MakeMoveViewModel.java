package interface_adapter.make_move;

import entity.ChessButton;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeMoveViewModel{
    private MakeMoveState state = new MakeMoveState();

    public MakeMoveViewModel() {}
    public void setState(MakeMoveState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("MakeMove", null, this.state);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public MakeMoveState getState() {
        return state;
    }
}
