package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualizzaPrenotatiEsameUI {
    public VisualizzaPrenotatiEsameUI() {}
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    @FXML
    private Button BottoneIndietroPrenotatiEsame;

    @FXML
    public void IndietroPaginaDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocenteView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Pagina Docente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroPrenotatiEsame.getScene().getWindow();
        currentStage.close();
    }
}
