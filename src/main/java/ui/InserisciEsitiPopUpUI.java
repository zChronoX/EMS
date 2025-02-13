package ui;

import classi.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class InserisciEsitiPopUpUI implements Initializable {
    private EMS ems;
    private Appello_esame appello;

    //public void setAppello(Appello_esame appello) {
    //    this.appello = appello;
    //}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        //recuperare appello d'esame
        appello=ems.getAppelloSelezionato();
    }

    public InserisciEsitiPopUpUI() {}

    @FXML
    private TextField matricolaTextField;

    @FXML
    private TextField votoTextField;

    @FXML
    private TextField statoTextField;

    @FXML
    private Button confermaButton; // Definisci il bottone "Conferma"

    @FXML
    private Button annullaButton;

    @FXML
    private void confermaInserimento(ActionEvent event) {
        String matricola = matricolaTextField.getText();
        String votoString = votoTextField.getText();
        String stato = statoTextField.getText();

        // 1. Validazione input
        if (matricola == null || matricola.isEmpty() ||
                votoString == null || votoString.isEmpty() ||
                stato == null || stato.isEmpty()) {
            showAlert("Errore", "Compila tutti i campi.");
            return;
        }

        try {
            int voto = Integer.parseInt(votoString);
            if (voto < 0 || voto > 30) {
                showAlert("Errore", "Voto non valido (0-30).");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Errore", "Voto non valido.");
            return;
        }

        // 2. Recupero studente
        Studente studente = ems.getStudente(matricola);
        if (studente == null) {
            showAlert("Errore", "Studente non trovato.");
            return;
        }

        // 3. Recupero prenotazione
        Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello(studente, appello);
        if (prenotazione == null) {
            showAlert("Errore", "Prenotazione non trovata.");
            return;
        }

        // 4. Creazione Esito_esame
        Esito_esame esito = new Esito_esame(votoString, stato, studente, appello);

        // 5. Inserimento esito (con controllo esistenza)
        try {
            ems.inserisciEsito(prenotazione.getID_prenotazione(), esito); // Inserimento con gestione eccezioni
            showAlert("Successo", "Esito inserito con successo.");
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Errore", "Errore durante l'inserimento: " + e.getMessage()); // Gestione eccezioni
        }
    }

    @FXML
    private void annullaInserimento(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
