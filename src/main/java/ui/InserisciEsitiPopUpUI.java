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

        if (matricola == null || matricola.isEmpty() ||
                votoString == null || votoString.isEmpty() ||
                stato == null || stato.isEmpty()) {
            showAlert("Errore", "Compila tutti i campi.");
            return;
        }

        try {
            int voto = Integer.parseInt(votoString); // Converti in intero per la validazione
            if (voto < 0 || voto > 30) {
                showAlert("Errore", "Voto non valido (0-30).");
                return;
            }

            Studente studente = ems.getStudente(matricola); // Usa la funzione esistente in EMS
            if (studente == null) {
                showAlert("Errore", "Studente non trovato.");
                return;
            }

            if (!appello.isStudentePrenotato(studente)) { // Verifica la prenotazione
                showAlert("Errore", "Lo studente non è prenotato a questo appello.");
                return;
            }

            if (ems.getEsitoByStudente(studente, appello) != null) { // Usa EMS per verificare l'esito
                showAlert("Errore", "Esito già presente per questo studente.");
                return;
            }

            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello(studente, appello);
            if (prenotazione == null) {
                showAlert("Errore", "Prenotazione non trovata.");
                return;
            }

            // Controllo aggiuntivo: verifica se esiste già un esito per questa prenotazione
            if (ems.getEsitoByPrenotazione(prenotazione) != null) {
                showAlert("Errore", "Esito già presente per questa prenotazione.");
                return; // Interrompi l'inserimento se l'esito è già presente
            }

            Esito_esame esito = new Esito_esame(votoString, stato, studente, appello);

            try {
                ems.inserisciEsito(prenotazione.getID_prenotazione(), esito);
                showAlert("Successo", "Esito inserito con successo.");
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                showAlert("Errore", "Errore nell'inserimento dell'esito: " + e.getMessage());
            }


        } catch (NumberFormatException e) {
            showAlert("Errore", "Voto non valido.");
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
