package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VisualizzaPrenotazioniAppelloUI implements Initializable {

    private Insegnamento insegnamento;
    private Studente studente;
    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        studente = ems.getStudenteCorrente();
        System.out.println("UIListaAppelli: Studente (set): " + studente);

        //RECUPERARE INSEGNAMENTO SELEZIONATO DOPO FARE LE ISTRUZIONI CHE SEGUONO
        insegnamento=ems.getInsegnamentoSelezionato();

        visualizzaInformazioniInsegnamento();
        visualizzaAppelliPrenotati();
    }

    @FXML
    private Label nomeInsegnamentoLabel;

    @FXML
    private Label codiceInsegnamentoLabel;

    @FXML
    private Label cfuInsegnamentoLabel;

    @FXML
    private ListView<String> appelliPrenotatiListView;

    @FXML
    private Button BottoneIndietroRicercaInsegnamento;

    @FXML
    private Button cancellaPrenotazioneButton;

    @FXML
    private Button rifiutaEsitoButton;

    private void visualizzaInformazioniInsegnamento() {
        if (insegnamento != null) {
            nomeInsegnamentoLabel.setText("Insegnamento: " + insegnamento.getNome());
            codiceInsegnamentoLabel.setText("Codice: " + insegnamento.getID_insegnamento());
            cfuInsegnamentoLabel.setText("CFU: " + insegnamento.getCFU());
        }
    }

    private void visualizzaAppelliPrenotati() {
        if (studente != null && insegnamento != null) {
            //provare a spostare getAppelli in ems
            //List<Appello_esame> appelliPrenotati = studente.getAppelli(); // Ottieni tutti gli appelli prenotati dallo studente
            List<Appello_esame> appelliPrenotati = ems.getAppelli();
            appelliPrenotatiListView.getItems().clear(); // Pulisci la lista

            if (appelliPrenotati == null || appelliPrenotati.isEmpty()) {
                appelliPrenotatiListView.getItems().add("Non hai prenotazioni per questo insegnamento.");
                rifiutaEsitoButton.setVisible(false); // Nascondi il bottone se non ci sono appelli
                return;
            }

            for (Appello_esame appello : appelliPrenotati) {
                ems.setAppelloCorrente(appello);
                if (appello.getInsegnamento().equals(insegnamento)) {
                    Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();

                    System.out.println("Appello: " + appello.getID_appello());
                    System.out.println("  Prenotazione recuperata: " + prenotazione); // Stampa l'oggetto Prenotazione

                    String esitoString = "";
                    if (prenotazione != null && prenotazione.getEsito() != null) {
                        esitoString = " - Esito: " + prenotazione.getEsito().getVoto() + " (" + prenotazione.getEsito().getStato() + ")";
                    }

                    appelliPrenotatiListView.getItems().add(appello.toString() + esitoString);
                }
            }
        }
    }

    @FXML
    private void mostraPopUpRifiuto(ActionEvent event) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Rifiuto Esito");

        VBox vbox = new VBox();
        TextField idAppelloTextField = new TextField();
        idAppelloTextField.setPromptText("ID Appello");
        vbox.getChildren().add(idAppelloTextField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String idAppello = idAppelloTextField.getText();
                if (idAppello.isEmpty()) {
                    showAlert("Errore", "Inserisci l'ID dell'appello.");
                    return;
                }
                rifiutaEsito(idAppello);
            }
        });
    }

    private void rifiutaEsito(String idAppello) {
        try {
            Appello_esame appello = ems.getAppelloById(idAppello);
            if (appello == null) {
                showAlert("Errore", "Nessun appello trovato con questo ID.");
                return;
            }

            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();
            if (prenotazione == null) {
                showAlert("Errore", "Nessuna prenotazione trovata per questo appello.");
                return;
            }

            if (prenotazione.getEsito() == null) {
                showAlert("Errore", "Non hai ricevuto nessun esito per questo appello."); // Messaggio di errore specifico
                return;
            }

            if (!prenotazione.getEsito().getStato().equals("Approvato")) {
                showAlert("Errore", "L'esito può essere rifiutato solo se è nello stato 'Approvato'.");
                return;
            }

            ems.rifiutaEsito(prenotazione.getEsito());
            showAlert("Successo", "Esito rifiutato con successo.");
            visualizzaAppelliPrenotati();

        } catch (Exception e) {
            showAlert("Errore", "Errore durante il rifiuto: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void cancellaPrenotazione(String idAppello) {
        try {
            Appello_esame appelloDaCancellare = ems.getAppelloById(idAppello);
            if (appelloDaCancellare == null) {
                showAlert("Errore", "Nessun appello trovato con questo ID.");
                return;
            }

            if (haRicevutoEsito(appelloDaCancellare, studente)) {
                showAlert("Errore", "Non puoi cancellare la prenotazione perché hai già ricevuto un esito per questo appello.");
                return;
            }

            // Controllo sulla data
            if (isTroppoTardiPerCancellare(appelloDaCancellare)) {
                showAlert("Errore", "Non puoi cancellare la prenotazione a meno di 3 giorni dalla data dell'appello.");
                return;
            }

            ems.cancellaPrenotazione(studente, appelloDaCancellare);
            showAlert("Successo", "Prenotazione cancellata con successo.");

            visualizzaAppelliPrenotati(); // Aggiorna la lista

        } catch (Exception e) {
            showAlert("Errore", "Errore durante la cancellazione: " + e.getMessage());
        }
    }

    private boolean isTroppoTardiPerCancellare(Appello_esame appello) {
        if (appello == null || appello.getData() == null) {
            return true; // Gestisce il caso in cui l'appello o la data sono null
        }

        LocalDate dataAppello = appello.getData(); // Ottiene la data come LocalDate
        LocalDate oggi = LocalDate.now();

        // ChronoUnit per calcolare la differenza in giorni
        long giorniDiDifferenza = ChronoUnit.DAYS.between(oggi, dataAppello);

        return giorniDiDifferenza < 3; // Restituisce true se mancano meno di 3 giorni
    }

    private boolean haRicevutoEsito(Appello_esame appello, Studente studente) {
        for (Prenotazione prenotazione : ems.getReservation_list().values()) {
            if (prenotazione.getAppello().equals(appello) && prenotazione.getStudente().equals(studente)) {
                return prenotazione.getEsito() != null; // Restituisce true se l'esito è presente
            }
        }
        return false; // Nessuna prenotazione trovata per questo studente e appello
    }


    @FXML
    private void mostraPopUpCancellazione(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cancellazione Prenotazione");

        VBox vbox = new VBox();
        TextField idAppelloTextField = new TextField();
        idAppelloTextField.setPromptText("ID Appello");
        vbox.getChildren().add(idAppelloTextField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String idAppello = idAppelloTextField.getText();
                if (idAppello.isEmpty()) {
                    showAlert("Errore", "Inserisci l'ID dell'appello.");
                    return;
                }
                cancellaPrenotazione(idAppello); // Passa la stringa
            }
        });
    }

    @FXML
    public void IndietroCercaInsegnamento() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotazioniInsegnamentoView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Prenotazione Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroRicercaInsegnamento.getScene().getWindow();
        currentStage.close();
    }
}
