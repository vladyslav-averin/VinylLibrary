package model.state;

import model.Vinyl;

public class AvailableState implements VinylState {

    @Override
    public void borrow(Vinyl vinyl, String user) {
        // The vinyl is available, so the user can borrow it
        vinyl.setBorrower(user);
        // Change state to "Borrowed"
        vinyl.setState(new BorrowedState());
    }

    @Override
    public void reserve(Vinyl vinyl, String user) {
        // If the vinyl is marked for removal, it cannot be reserved
        if (vinyl.isToBeRemoved()) {
            return; // Do nothing
        }

        // Otherwise, the user reserves it
        vinyl.setReserver(user);
        // Change state to "Reserved"
        vinyl.setState(new ReservedState());
    }

    @Override
    public void returnVinyl(Vinyl vinyl, String user) {
        // The vinyl is already in the library, so it cannot be returned. Do nothing.
    }

    @Override
    public void remove(Vinyl vinyl) {
        // The vinyl is available and not reserved.
        // According to the rules, it can be removed.
        // Set the flag. The Library model will detect this via Observer and remove it from the list.
        vinyl.setToBeRemoved(true);
    }

    @Override
    public String getStatusString() {
        return "Available";
    }
}