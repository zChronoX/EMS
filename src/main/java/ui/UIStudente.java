package ui;

import classi.EMS;
import classi.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UIStudente {

    public UIStudente() { }

    private EMS ems; // Istanza di EMS
    private Studente studente;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
        visualizzaInformazioniStudente();
    }

    @FXML
    private Button BottonePrenotazioneStudente;
    @FXML
    private Button BottoneVisualizzaCarriera;
    @FXML
    private Button BottoneAnnullaPrenotazione;
    @FXML
    private Button BottoneVisualizzaInfoEsame;
    @FXML
    private Button BottoneRifiutaEsito;
    @FXML
    private Button BottoneInviaFeedback;
    @FXML
    private Button BottoneModificaProfilo;
    @FXML
    private Button BottoneLogoutStudente;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label matricolaLabel;


    private void visualizzaInformazioniStudente() {
        if (studente != null) {
            // Esempio: visualizzazione in Label
            nomeLabel.setText("Nome: " + studente.getNome());
            cognomeLabel.setText("Cognome: " + studente.getCognome());
            matricolaLabel.setText("Matricola: " + studente.getMatricola());

        }
    }

    @FXML
    public void ApriPrenotazioneStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrenotazioneAppelloView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        UIPrenotazioneAppello controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Prenotazione Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottonePrenotazioneStudente.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriVisualizzaInfoEsame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotazioniInsegnamentoView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaPrenotazioniInsegnamentoUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Visualizza info Esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaInfoEsame.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void LogoutStudente() throws IOException {
        Stage primaryStage = (Stage) BottoneLogoutStudente.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }
}
