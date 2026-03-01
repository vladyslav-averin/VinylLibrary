package model.state;

import model.Vinyl;

public class ReservedState implements VinylState {

    @Override
    public void borrow(Vinyl vinyl, String user) {
        // Check if the user who wants to borrow is the same user who reserved it
        if (user.equals(vinyl.getReserver())) {
            vinyl.setBorrower(user);
            vinyl.setReserver(null); // Clear the reservation
            vinyl.setState(new BorrowedState());
        }
    }

    @Override
    public void reserve(Vinyl vinyl, String user) {
        // Already reserved. Only one user can reserve.
    }

    @Override
    public void returnVinyl(Vinyl vinyl, String user) {
        // It is already in the library. Cannot return it.
    }

    @Override
    public void remove(Vinyl vinyl) {
        // Cannot remove immediately because it is reserved. Set the flag for future removal.
        vinyl.setToBeRemoved(true);
    }

    @Override
    public String getStatusString() {
        return "Reserved";
    }
}