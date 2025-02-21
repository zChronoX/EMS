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
        appello=ems.getAppelloCorrente();
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
        String voto = votoTextField.getText(); // Manteniamo voto come String
        String stato = statoTextField.getText();

        // 1. Validazione input
        if (matricola == null || matricola.isEmpty() ||
                voto == null || voto.isEmpty() ||
                stato == null || stato.isEmpty()) {
            showAlert("Errore", "Compila tutti i campi.");
            return;
        }

        // 2. Controllo che voto sia un numero tra 0 e 30
        try {
            int votoNum = Integer.parseInt(voto); // Conversione temporanea
            if (votoNum < 0 || votoNum > 30) {
                showAlert("Errore", "Il voto deve essere compreso tra 0 e 30.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Errore", "Il voto deve essere un numero valido.");
            return;
        }

        // 3. Recupero studente
        Studente studente = ems.getStudente(matricola);
        if (studente == null) {
            showAlert("Errore", "Studente non trovato.");
            return;
        }

        // 4. Controllo che l'appello sia stato selezionato
        if (appello == null) {
            showAlert("Errore", "Nessun appello selezionato.");
            return;
        }

        // 5. Recupero prenotazione
        Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();
        if (prenotazione == null) {
            showAlert("Errore", "Lo studente non è prenotato a questo appello.");
            return;
        }

        // 6. Inserimento esito
        try {
            ems.inserisciEsito(matricola, voto, stato); // Passiamo voto come String
            showAlert("Successo", "Esito inserito con successo.");

            // Chiudi la finestra solo se tutto è andato bene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Errore", "Errore durante l'inserimento: " + e.getMessage());
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
