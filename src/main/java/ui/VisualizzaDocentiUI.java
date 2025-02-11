package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualizzaDocentiUI {
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
        displayDocentiList(); // Chiama questo metodo *dopo* aver impostato EMS
    }

    @FXML
    private Button BottoneIndietroDocentiView;
    @FXML
    private TextArea docentiListTextArea;

    public void displayDocentiList() {
        if (ems != null) {
            docentiListTextArea.setText(ems.stampa_docentiView());
        } else {
            docentiListTextArea.setText("Istanza EMS nulla. Impossibile visualizzare i docenti.");
        }
    }

    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems); // Passa l'istanza di EMS
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroDocentiView.getScene().getWindow();
        currentStage.close();
    }
}