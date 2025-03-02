package ui;

import classi.Docente;
import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UIDocente implements Initializable {
    private EMS ems;
    private Docente docente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        docente=ems.getDocenteCorrente();
        ems.setUtenteCorrente(docente);
        visualizzaInformazioniDocente();
    }

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label codiceDocenteLabel;

    private void visualizzaInformazioniDocente() {
        if (docente != null) {
            nomeLabel.setText("Nome: " + docente.getNome());
            cognomeLabel.setText("Cognome: " + docente.getCognome());
            codiceDocenteLabel.setText("Codice Docente: " + docente.getCodiceDocente());

        }
    }
    public UIDocente() {}

    @FXML
    private Button BottoneVisualizzaListaPrenotati;
    @FXML
    private Button BottoneVisualizzaFeedback;
    @FXML
    private Button BottoneLogoutDocente;
    @FXML
    private Button BottoneModificaProfilo;

    @FXML
    public void LogoutDocente() throws IOException {
        Stage primaryStage = (Stage) BottoneLogoutDocente.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("EMS");
    }
    @FXML
    public void ApriElencoPrenotati() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotatiEsameView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaListaPrenotati.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void ApriListaFeedback() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaFeedbackEsameView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza feedback esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaFeedback.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriModificaProfilo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModificaProfiloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Modifica profilo");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneModificaProfilo.getScene().getWindow();
        currentStage.close();
    }


}