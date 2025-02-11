package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualizzaStudentiUI {
    public VisualizzaStudentiUI() {}

    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
        displayStudentList();
    }

    @FXML
    private Button BottoneIndietroStudentiView;
    @FXML
    private TextArea studentListTextArea; // Assuming you have a TextArea in your FXML

    public void displayStudentList() { // New method to display students
        if (ems != null) {
            studentListTextArea.setText(ems.stampa_studentiView());
        } else {
            studentListTextArea.setText("EMS instance is null. Cannot display students.");
        }
    }

    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroStudentiView.getScene().getWindow();
        currentStage.close();
    }

}
