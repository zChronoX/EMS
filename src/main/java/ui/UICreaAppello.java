package ui;

import classi.Appello_esame;
import classi.Docente;
import classi.EMS;
import classi.Insegnamento;
import com.fasterxml.jackson.core.JsonParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UICreaAppello implements Initializable {

    @FXML
    private TextField idInsegnamentoTextField;

    @FXML
    private Label codiceInsegnamentoLabel;

    @FXML
    private Button cercaInsegnamentoButton;

    @FXML
    private TextField idAppelloTextField;
    @FXML
    private TextField orarioTextField;
    @FXML
    private DatePicker dataDatePicker;
    @FXML
    private TextField luogoTextField;
    @FXML
    private TextField postiTextField;
    @FXML
    private TextField tipologiaTextField;
    @FXML
    private Button nuovaRicercaButton;
    @FXML
    private Button confermaButton;
    @FXML
    private Button BottoneIndietroWelcomeViewAppello;
    @FXML
    private ListView<String> docentiListView; // ListView per visualizzare i docenti*/



    private EMS ems;
    private Insegnamento insegnamento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();

    }

    //verificare caricamento insegnamenti --> dovrebbero essere caricati

    //cosa succede quando premo "Cerca insegnamento"
    @FXML
    private void handleCercaInsegnamento(ActionEvent event) {

        String codice = idInsegnamentoTextField.getText();
        HashMap<String, Insegnamento> insegnamenti = ems.getInsegnamenti();

        if (insegnamenti != null && insegnamenti.containsKey(codice)) {
             insegnamento = insegnamenti.get(codice);

            //docentiListView.getItems().clear(); capire se serve
            idInsegnamentoTextField.clear(); //capire se serve
            List<Docente> docenti = insegnamento.getDocenti();

            if (docenti != null && !docenti.isEmpty()) {
                for (Docente docente : docenti) {
                    docentiListView.getItems().add(docente.getNome() + " " + docente.getCognome());
                }
            } else {
                docentiListView.getItems().add("Nessun docente assegnato.");
            }

            codiceInsegnamentoLabel.setVisible(false);
            idInsegnamentoTextField.setVisible(false);
            cercaInsegnamentoButton.setVisible(false);
            // Mostra i campi e il popup
            mostraCampi();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insegnamento trovato");
            alert.setHeaderText("ID Valido");
            alert.setContentText("Inserisci le informazioni dell'appello di " + insegnamento.getNome());
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Insegnamento non trovato");
            alert.setContentText("L'insegnamento con il codice " + codice + " non esiste.");
            alert.showAndWait();

            idInsegnamentoTextField.clear();
            docentiListView.getItems().clear();
        }
    }

    private void mostraCampi() {
        idAppelloTextField.setVisible(true);
        orarioTextField.setVisible(true);
        dataDatePicker.setVisible(true);
        luogoTextField.setVisible(true);
        postiTextField.setVisible(true);
        tipologiaTextField.setVisible(true);
        docentiListView.setVisible(true);
        nuovaRicercaButton.setVisible(true);
        confermaButton.setVisible(true);
    }

    private int generateRandomInt(int digits) {
        Random random = new Random();
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return min + random.nextInt(max - min + 1);
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        try {
            if (insegnamento == null) {
                mostraErrore("Nessun insegnamento selezionato", "Seleziona prima l'insegnamento.");
                return;
            }

            int idAppello = generateRandomInt(10);
            idAppelloTextField.setText(String.valueOf(idAppello));

            LocalTime orario = null;
            try {
                orario = LocalTime.parse(orarioTextField.getText());
            } catch (DateTimeParseException e) {
                mostraErrore("Formato orario non valido", "Inserisci l'orario nel formato HH:MM.");
                return;
            }

            LocalDate data = dataDatePicker.getValue();
            if (data == null) {
                mostraErrore("Data non selezionata", "Seleziona una data per l'appello.");
                return;
            }

            String luogo = luogoTextField.getText();

            int posti = 0;
            try {
                posti = Integer.parseInt(postiTextField.getText());
                if (posti <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                mostraErrore("Posti non validi", "Inserisci un numero intero positivo per i posti.");
                return;
            }

            String tipologia = tipologiaTextField.getText();

            // Controllo aggiuntivo di insegnamento prima dell'uso
            if (insegnamento == null) {
                mostraErrore("Errore interno", "L'insegnamento è diventato null durante l'elaborazione.");
                return;
            }

            Appello_esame appello = new Appello_esame(String.valueOf(idAppello), orario, data, luogo, posti, tipologia, insegnamento);
            System.out.println("Appello creato: " + appello); // Stampa l'oggetto appello
            insegnamento.aggiungiAppello(appello);
            System.out.println("Appelli dell'insegnamento: " + insegnamento.getExam_list()); // Stampa la lista degli appelli

            // Popup di conferma (simile a onBottoneConferma)
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Riepilogo Informazioni Appello");

            String dati = "ID: " + appello.getID_appello() + "\n" +
                    "Insegnamento: " + insegnamento.getNome() + "\n" +
                    "Orario: " + appello.getOrario() + "\n" +
                    "Data: " + appello.getData() + "\n" +
                    "Luogo: " + appello.getLuogo() + "\n" +
                    "Posti: " + appello.getPostiDisponibili() + "\n" +
                    "Tipologia: " + appello.getTipologia();

            dialog.setContentText(dati);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Usa setOnShown() per gestire la chiusura dello Stage *dopo* che è stato mostrato
                dialog.setOnShown(e -> {
                    Stage alertStage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    if (alertStage != null) {
                        alertStage.close();
                    }
                    ems.stampa_tutti_gli_appelli();
                });
            }


        } catch (Exception e) {
            e.printStackTrace(); // Stampa l'errore sulla console (utile per il debug)
            mostraErrore("Errore durante la creazione dell'appello", e.getMessage()); // Mostra un messaggio all'utente
        }

    }

    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    @FXML
    private void handleNuovaRicerca(ActionEvent event) {
        nascondiCampi();
        insegnamento = null;
        idInsegnamentoTextField.clear();
        docentiListView.getItems().clear();
        codiceInsegnamentoLabel.setVisible(true);
        idInsegnamentoTextField.setVisible(true);
        cercaInsegnamentoButton.setVisible(true);

    }

    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroWelcomeViewAppello.getScene().getWindow();
        currentStage.close();
    }

    private void nascondiCampi() {
        idAppelloTextField.setVisible(false);
        orarioTextField.setVisible(false);
        dataDatePicker.setVisible(false);
        luogoTextField.setVisible(false);
        postiTextField.setVisible(false);
        tipologiaTextField.setVisible(false);
        docentiListView.setVisible(false);
        nuovaRicercaButton.setVisible(false);
        confermaButton.setVisible(false);
    }


}