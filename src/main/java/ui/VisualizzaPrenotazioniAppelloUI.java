package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VisualizzaPrenotazioniAppelloUI {

    private Insegnamento insegnamento;
    private Studente studente; // Se gestisci il login
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
        studente = ems.getStudenteCorrente();
        System.out.println("UIListaAppelli: Studente (set): " + studente);
    }

    public void setStudente(Studente studente) {
        this.studente = studente;

    }

    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento;
        System.out.println("UIListaAppelli: Insegnamento (set): " + insegnamento);
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
            List<Appello_esame> appelliPrenotati = studente.getAppelli();

            appelliPrenotatiListView.getItems().clear();

            if (appelliPrenotati == null || appelliPrenotati.isEmpty()) {
                appelliPrenotatiListView.getItems().add("Non hai prenotazioni per questo insegnamento.");
                rifiutaEsitoButton.setVisible(false); // Nascondi il bottone se non ci sono appelli
                return;
            }

            for (Appello_esame appello : appelliPrenotati) {
                if (appello.getInsegnamento().equals(insegnamento)) {
                    Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello(studente, appello);

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

            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello(studente, appello);
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
            return true; // Gestisci il caso in cui l'appello o la data sono null
        }

        LocalDate dataAppello = appello.getData(); // Ottieni la data come LocalDate
        LocalDate oggi = LocalDate.now();

        // Usa ChronoUnit per calcolare la differenza in giorni
        long giorniDiDifferenza = ChronoUnit.DAYS.between(oggi, dataAppello);

        return giorniDiDifferenza < 3; // Restituisce true se mancano meno di 3 giorni
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
        VisualizzaPrenotazioniInsegnamentoUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Prenotazione Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroRicercaInsegnamento.getScene().getWindow();
        currentStage.close();
    }
}
