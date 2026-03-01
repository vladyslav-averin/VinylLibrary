package model;

import model.state.AvailableState;
import model.state.VinylState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Vinyl {
    private String title;
    private String artist;
    private int releaseYear;

    // State pattern context
    private VinylState state;

    // Additional data for logic rules
    private String borrower; // Who borrowed it
    private String reserver; // Who reserved it
    private boolean toBeRemoved; // Removal flag

    // Observer pattern support (to notify the library/ViewModel)
    private PropertyChangeSupport support;

    public Vinyl(String title, String artist, int releaseYear) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;

        // By default, the vinyl is available when created
        this.state = new AvailableState();
        this.support = new PropertyChangeSupport(this);
        this.toBeRemoved = false;
    }

    // --- Synchronized Delegate Methods (State Pattern & Thread Safety) ---
    // The synchronized keyword prevents race conditions when the model.UserSimulator
    // and the GUI attempt to modify the same vinyl at the same time.

    public synchronized void borrow(String user) {
        state.borrow(this, user);
    }

    public synchronized void reserve(String user) {
        state.reserve(this, user);
    }

    public synchronized void returnVinyl(String user) {
        state.returnVinyl(this, user);
    }

    public synchronized void remove() {
        state.remove(this);
    }

    // --- State and Property Modifiers ---

    // Changes the state and notifies listeners (Observer Pattern)
    public synchronized void setState(VinylState newState) {
        VinylState oldState = this.state;
        this.state = newState;
        // Notify listeners (the Library) that the state has changed
        support.firePropertyChange("state", oldState, newState);
    }

    public synchronized boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public synchronized void setToBeRemoved(boolean toBeRemoved) {
        boolean old = this.toBeRemoved;
        this.toBeRemoved = toBeRemoved;
        // Notify listeners that the removal flag was updated
        support.firePropertyChange("toBeRemoved", old, toBeRemoved);
    }

    // --- Getters and Setters ---

    public synchronized String getBorrower() {
        return borrower;
    }

    public synchronized void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public synchronized String getReserver() {
        return reserver;
    }

    public synchronized void setReserver(String reserver) {
        this.reserver = reserver;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public synchronized VinylState getState() {
        return state;
    }

    // --- Observer Pattern Methods ---

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // --- Helper Method ---
    // Formats the status for the View to display in the table
    public synchronized String getStatus() {
        String baseStatus = state.getStatusString();
        // Show that the vinyl will be removed soon if the flag is active
        if (toBeRemoved) {
            return baseStatus + " (Marked for removal)";
        }
        return baseStatus;
    }
}