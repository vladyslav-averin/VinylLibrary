package viewmodel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Session;
import model.Vinyl;
import model.VinylLibrary;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainViewModel implements PropertyChangeListener {
    private VinylLibrary model;
    private Session session;

    // Special list for JavaFX. When it changes, the table in the GUI updates automatically
    private ObservableList<Vinyl> vinylList;

    public MainViewModel(VinylLibrary model, Session session) {
        this.model = model;
        this.session = session;

        // Load the initial list of vinyls from the library
        this.vinylList = FXCollections.observableArrayList(model.getVinyls());

        // Subscribe to updates from the Model (Observer pattern)
        this.model.addPropertyChangeListener(this);
    }

    // Returns the list for displaying in the GUI table
    public ObservableList<Vinyl> getVinylList() {
        return vinylList;
    }

    // Save which vinyl was selected in the table
    public void setSelectedVinyl(Vinyl vinyl) {
        session.setSelectedVinyl(vinyl);
    }

    // Methods for GUI buttons

    public void borrowSelected() {
        Vinyl selected = session.getSelectedVinyl();
        if (selected != null) {
            selected.borrow(session.getLoggedInUser());
        }
    }

    public void reserveSelected() {
        Vinyl selected = session.getSelectedVinyl();
        if (selected != null) {
            selected.reserve(session.getLoggedInUser());
        }
    }

    public void returnSelected() {
        Vinyl selected = session.getSelectedVinyl();
        if (selected != null) {
            selected.returnVinyl(session.getLoggedInUser());
        }
    }

    public void removeSelected() {
        Vinyl selected = session.getSelectedVinyl();
        if (selected != null) {
            selected.remove();
        }
    }

    // React to changes in the Model (Observer)
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Platform.runLater makes sure the list updates in the correct JavaFX thread
        Platform.runLater(() -> {
            // Simply reload the full list of vinyls from the model
            vinylList.setAll(model.getVinyls());
        });
    }
}