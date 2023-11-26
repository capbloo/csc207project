package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {
    private String currentViewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getCurrentView() {
        return currentViewName;
    }

    public void setCurrentView(String newView) {
        this.currentViewName = newView;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.currentViewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
