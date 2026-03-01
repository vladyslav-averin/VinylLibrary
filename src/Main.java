import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Session;
import model.UserSimulator;
import model.VinylLibrary;
import view.MainViewController;
import viewmodel.MainViewModel;

public class Main extends Application {
    private UserSimulator simulator;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the Model (Library)
        VinylLibrary library = new VinylLibrary();

        // Create a model.Session to track the selected vinyl and current user
        Session session = new Session();

        // Create the ViewModel
        MainViewModel viewModel = new MainViewModel(library, session);

        // Load the UI (MainView.fxml file)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));
        Scene scene = new Scene(loader.load());

        // Pass the ViewModel to the Controller
        MainViewController controller = loader.getController();
        controller.init(viewModel);

        // Start the user simulator in a separate thread
        simulator = new UserSimulator(library);
        Thread simThread = new Thread(simulator);
        // Make it a daemon thread so it stops automatically when the application window is closed
        simThread.setDaemon(true);
        simThread.start();

        // Show the window
        primaryStage.setTitle("model.Vinyl Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Stop the simulation when the application closes
        if (simulator != null) {
            simulator.stopSimulation();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}