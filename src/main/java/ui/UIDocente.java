package ui;

import classi.Docente;
import classi.EMS;
import classi.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UIDocente {

    public UIDocente() {}

    private EMS ems;
    private Docente docente;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
        visualizzaInformazioniDocente();
    }

    @FXML
    private Button BottoneGestisciEsiti;
    @FXML
    private Button BottoneVisualizzaListaPrenotati;
    @FXML
    private Button BottoneVisualizzaFeedback;
    @FXML
    private Button BottoneLogoutDocente;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label codiceDocenteLabel;


    private void visualizzaInformazioniDocente() {
        if (docente != null) {
            // Esempio: visualizzazione in Label
            nomeLabel.setText("Nome: " + docente.getNome());
            cognomeLabel.setText("Cognome: " + docente.getCognome());
            codiceDocenteLabel.setText("Matricola: " + docente.getCodiceDocente());

        }
    }


    @FXML
    public void LogoutDocente() throws IOException {
        Stage primaryStage = (Stage) BottoneLogoutDocente.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }
    @FXML
    public void ApriElencoPrenotati() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotatiEsameView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaPrenotatiEsameUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaListaPrenotati.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void ApriListaFeedback() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaFeedbackEsameView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaFeedbackEsameUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Visualizza feedback esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaFeedback.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void ApriGestioneEsiti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GestisciEsitiView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        GestisciEsitiUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Gestisci esiti esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneGestisciEsiti.getScene().getWindow();
        currentStage.close();
    }

}