package model;

import java.util.List;
import java.util.Random;

public class UserSimulator implements Runnable {
    private VinylLibrary library;
    private boolean running;
    private String[] fakeUsers = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
    private Random random;

    public UserSimulator(VinylLibrary library) {
        this.library = library;
        this.running = true;
        this.random = new Random();
    }

    // Method to safely stop the thread when the program closes
    public void stopSimulation() {
        this.running = false;
    }

    @Override
    public void run() {
        // Endless loop simulating user activity
        while (running) {
            try {
                // Random pause from 2 to 5 seconds (simulating random intervals)
                Thread.sleep(random.nextInt(3000) + 2000);

                // Get the current list of vinyls from the library
                List<Vinyl> vinyls = library.getVinyls();
                if (vinyls.isEmpty()) {
                    continue; // If the library is empty, just wait
                }

                // Select a random vinyl
                Vinyl randomVinyl = vinyls.get(random.nextInt(vinyls.size()));

                // Select a random "user"
                String randomUser = fakeUsers[random.nextInt(fakeUsers.length)];

                // Generate a number from 0 to 9 to make removal a rare event
                int action = random.nextInt(10);

                switch (action) {
                    case 0: case 1: case 2: // 30% chance
                        randomVinyl.borrow(randomUser);
                        System.out.println(randomUser + " tries to BORROW " + randomVinyl.getTitle());
                        break;
                    case 3: case 4: case 5: // 30% chance
                        randomVinyl.reserve(randomUser);
                        System.out.println(randomUser + " tries to RESERVE " + randomVinyl.getTitle());
                        break;
                    case 6: case 7: case 8: // 30% chance
                        randomVinyl.returnVinyl(randomUser);
                        System.out.println(randomUser + " tries to RETURN " + randomVinyl.getTitle());
                        break;
                    case 9: // 10% chance
                        randomVinyl.remove();
                        System.out.println("Someone tries to REMOVE " + randomVinyl.getTitle());
                        break;
                }

            } catch (InterruptedException e) {
                System.out.println("Simulation stopped.");
                break;
            }
        }
    }
}