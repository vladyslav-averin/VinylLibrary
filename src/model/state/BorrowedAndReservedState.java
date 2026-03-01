package model.state;

import model.Vinyl;

public class BorrowedAndReservedState implements VinylState {

    @Override
    public void borrow(Vinyl vinyl, String user) {
        // Already borrowed. Can't borrow again.
    }

    @Override
    public void reserve(Vinyl vinyl, String user) {
        // Reservation queue is full (only one user can reserve)
    }

    @Override
    public void returnVinyl(Vinyl vinyl, String user) {
        // Check that the user returning it is the borrower, not the reserver or someone else
        if (user.equals(vinyl.getBorrower())) {
            vinyl.setBorrower(null);
            // The vinyl is returned, but since it is reserved, it changes to "Reserved" state
            vinyl.setState(new ReservedState());
        }
    }

    @Override
    public void remove(Vinyl vinyl) {
        // Set removal flag
        vinyl.setToBeRemoved(true);
    }

    @Override
    public String getStatusString() {
        return "Borrowed & Reserved";
    }
}