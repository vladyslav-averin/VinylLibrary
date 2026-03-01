package model;

public class Session {
    // Name of the user we act as in the GUI
    private String loggedInUser;

    // The vinyl selected in the list
    private Vinyl selectedVinyl;

    public Session() {
        this.loggedInUser = "MainUser"; // This is me
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public Vinyl getSelectedVinyl() {
        return selectedVinyl;
    }

    public void setSelectedVinyl(Vinyl selectedVinyl) {
        this.selectedVinyl = selectedVinyl;
    }
}