package model.state;

import model.Vinyl;

public interface VinylState {
    // Try to borrow the vinyl
    void borrow(Vinyl vinyl, String user);

    // Try to reserve the vinyl
    void reserve(Vinyl vinyl, String user);

    // Try to return the vinyl
    void returnVinyl(Vinyl vinyl, String user);

    // Try to remove the vinyl from the library
    void remove(Vinyl vinyl);

    // Helper method for the GUI to display the status
    String getStatusString();
}