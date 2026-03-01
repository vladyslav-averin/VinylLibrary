package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Vinyl;
import viewmodel.MainViewModel;

public class MainViewController {
    @FXML private TableView<Vinyl> vinylTable;
    @FXML private TableColumn<Vinyl, String> titleColumn;
    @FXML private TableColumn<Vinyl, String> artistColumn;
    @FXML private TableColumn<Vinyl, Integer> yearColumn;
    @FXML private TableColumn<Vinyl, String> statusColumn;
    @FXML private TableColumn<Vinyl, String> borrowerColumn;
    @FXML private TableColumn<Vinyl, String> reserverColumn;

    private MainViewModel viewModel;

    // Initialize method (connects Controller with ViewModel)
    public void init(MainViewModel viewModel) {
        this.viewModel = viewModel;

        // Bind table columns to getters in the model.Vinyl class
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); // Uses getStatus()
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("borrower"));
        reserverColumn.setCellValueFactory(new PropertyValueFactory<>("reserver"));

        // Bind the list from ViewModel to the table
        // The table will update automatically
        vinylTable.setItems(viewModel.getVinylList());

        // Listen for table selection changes
        // When a vinyl is selected, pass it to model.Session via ViewModel
        vinylTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            viewModel.setSelectedVinyl(newSelection);
        });
    }

    // Button methods from FXML (Reserve, Borrow, Return, Remove)

    @FXML
    public void onReserveClicked() {
        viewModel.reserveSelected();
    }

    @FXML
    public void onBorrowClicked() {
        viewModel.borrowSelected();
    }

    @FXML
    public void onReturnClicked() {
        viewModel.returnSelected();
    }

    @FXML
    public void onRemoveClicked() {
        viewModel.removeSelected();
    }
}