package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class VinylLibrary implements PropertyChangeListener {
    private List<Vinyl> vinyls;
    private PropertyChangeSupport support;

    public VinylLibrary() {
        this.vinyls = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);

        // Pre-populating the library with data
        addVinyl(new Vinyl("Abbey Road", "The Beatles", 1969));
        addVinyl(new Vinyl("The Dark Side of the Moon", "Pink Floyd", 1973));
        addVinyl(new Vinyl("Thriller", "Michael Jackson", 1982));
        addVinyl(new Vinyl("Back in Black", "AC/DC", 1980));
        addVinyl(new Vinyl("Rumours", "Fleetwood Mac", 1977));
        addVinyl(new Vinyl("Hotel California", "Eagles", 1976));
        addVinyl(new Vinyl("Nevermind", "Nirvana", 1991));
        addVinyl(new Vinyl("Led Zeppelin IV", "Led Zeppelin", 1971));
        addVinyl(new Vinyl("A Night at the Opera", "Queen", 1975));
        addVinyl(new Vinyl("Sticky Fingers", "The Rolling Stones", 1971));
    }

    // Synchronized to prevent thread interference when adding items
    public synchronized void addVinyl(Vinyl vinyl) {
        vinyls.add(vinyl);
        // The library acts as an Observer, listening to changes inside this specific vinyl
        vinyl.addPropertyChangeListener(this);

        // Notify the ViewModel that the list has changed
        support.firePropertyChange("VinylAdded", null, vinyl);
    }

    // Synchronized to return a safe copy of the list for multiple threads
    public synchronized List<Vinyl> getVinyls() {
        return new ArrayList<>(vinyls);
    }

    // Methods for the Observer pattern (forwarding to ViewModel)
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // This method is triggered when a vinyl fires a property change (e.g. state changes)
    // Synchronized to protect the critical region where list modification happens
    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) {
        Vinyl sourceVinyl = (Vinyl) evt.getSource();

        // If the vinyl changed state to "Available" AND is marked for removal, remove it permanently
        if (sourceVinyl.getState().getStatusString().equals("Available") && sourceVinyl.isToBeRemoved()) {
            vinyls.remove(sourceVinyl);
            // Stop listening to it since it's removed
            sourceVinyl.removePropertyChangeListener(this);
            // Notify the ViewModel that the vinyl was permanently removed
            support.firePropertyChange("VinylRemoved", null, sourceVinyl);
        } else {
            // If it is just a normal state change (borrow/return/reserve), forward the notification
            support.firePropertyChange("VinylStateChanged", null, sourceVinyl);
        }
    }
}