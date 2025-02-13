package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaDocentiUI implements Initializable {
    private EMS ems;
    @FXML
    private Button BottoneIndietroDocentiView;
    @FXML
    private TextArea docentiListTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        docentiListTextArea.appendText(ems.stampa_docentiView());
        //displayDocentiList();
    }

    // FORSE NON SERVE
    /*public void displayDocentiList() {
        if (ems != null) {
            docentiListTextArea.setText(ems.stampa_docentiView());
        } else {
            docentiListTextArea.setText("Istanza EMS nulla. Impossibile visualizzare i docenti.");
        }
    }*/

    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroDocentiView.getScene().getWindow();
        currentStage.close();
    }
}