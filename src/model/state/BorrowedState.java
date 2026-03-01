package model.state;

import model.Vinyl;

public class BorrowedState implements VinylState {

    @Override
    public void borrow(Vinyl vinyl, String user) {
        // Do nothing. The vinyl is already borrowed by someone else.
    }

    @Override
    public void reserve(Vinyl vinyl, String user) {
        // If the vinyl is marked for removal, do not allow reservation
        if (vinyl.isToBeRemoved()) {
            return;
        }

        // Save who reserved it and change state
        vinyl.setReserver(user);
        vinyl.setState(new BorrowedAndReservedState());
    }

    @Override
    public void returnVinyl(Vinyl vinyl, String user) {
        // Check that the user returning it is the borrower
        if (user.equals(vinyl.getBorrower())) {
            vinyl.setBorrower(null);
            vinyl.setState(new AvailableState());
        }
    }

    @Override
    public void remove(Vinyl vinyl) {
        // Can't remove it right now because it is borrowed
        // Just set the flag to remove it later
        vinyl.setToBeRemoved(true);
    }

    @Override
    public String getStatusString() {
        return "Borrowed";
    }
}