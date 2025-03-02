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

public class VisualizzaStudentiUI implements Initializable {
    private EMS ems;
    @FXML
    private Button BottoneIndietroStudentiView;
    @FXML
    private TextArea studentListTextArea;

    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroStudentiView.getScene().getWindow();
        currentStage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        studentListTextArea.appendText(ems.stampa_studentiView());


    }

}
