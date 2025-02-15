package ui;

import classi.Appello_esame;
import classi.EMS;
import classi.Esito_esame;
import classi.Prenotazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

//FARE IN MODO CHE UNO STUDENTE NON POSSA RIMETTERE IL FEEDBACK
// PER LO STESSO ESAME MANTENENDO L'ANONIMO

public class UIInviaFeedback implements Initializable {
    private EMS ems;
    private HashMap<String, Prenotazione> prenotazioni;
    private Esito_esame esito;
    private Prenotazione prenotazione;
    //private Feedback feedback;

    @FXML
    private Button confermaButton;
    @FXML
    private TextField idPrenotazioneTextField;
    @FXML
    private ListView<String> sezionePrenotazioni;
    @FXML
    private Button BottoneIndietro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        prenotazioni = ems.getReservation_list();
        visualizzaPrenotazioni();
    }


    private void visualizzaPrenotazioni() {
        sezionePrenotazioni.getItems().clear(); // Pulisci la ListView!

        for (Prenotazione p : prenotazioni.values()) {
            Esito_esame esito = p.getEsito();
            if (esito != null && esito.getStato().equals("Approvato")) {
                prenotazione=p;
                String prenotazioneString = String.format("IDPrenotazione: %s, Data: %s, Insegnamento: %s, IDAppello: %s, Esito: %s, Voto: %s ",
                        p.getID_prenotazione(), p.getData(), p.getAppello().getInsegnamento().getNome(), p.getAppello().getID_appello(), p.getEsito().getStato(), p.getEsito().getVoto());
                sezionePrenotazioni.getItems().add(prenotazioneString);
            }
        }

        if (sezionePrenotazioni.getItems().isEmpty()) {
            sezionePrenotazioni.getItems().add("Nessun esame disponibile per l'invio di feedback");
        }
    }

    @FXML
    private void InviaFeedback() {
        String idPrenotazione = idPrenotazioneTextField.getText();

        // Verifica se l'ID è stato inserito
        if (idPrenotazione == null || idPrenotazione.isEmpty()) {
            // Mostra un messaggio di errore se l'ID non è stato inserito
            mostraAvviso("Errore", "Inserisci l'ID della prenotazione.");
            return; // Esci dalla funzione
        }

        //  Verifica se l'ID corrisponde a una prenotazione nella mappa
        if (prenotazioni.containsKey(idPrenotazione)) {
            // L'ID esiste, puoi procedere con l'invio del feedback
            Prenotazione prenotazione = prenotazioni.get(idPrenotazione);
            System.out.println("Prenotazione trovata: " + prenotazione.getID_prenotazione());


            // ... (aggiungi qui la logica per inviare il feedback)

            apriPopupFeedback();


        } else {
            mostraAvviso("Errore", "ID prenotazione non trovato.");
        }


    }

    // Funzione di supporto per mostrare avvisi
    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void apriPopupFeedback() {
        TextInputDialog dialog = new TextInputDialog("Inserisci il tuo feedback:");
        dialog.setTitle("Feedback");
        dialog.setHeaderText("Inserisci il tuo feedback:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            /*String descrizione=feedback.getDescrizione();
             descrizione = result.get();*/
            // Qui puoi gestire l'invio del feedback
            //System.out.println("Feedback inviato: " + descrizione);

            //devo recuperare l'appello relativo alla prenotazione???
            //a chi va associato il feedback??

            Appello_esame appello=prenotazione.getAppello();
           // List<String> recensioni=appello.getFeedbacks();
            appello.addFeedback(result.get());

        }
    }

    @FXML
    public void Indietro() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Pagina Studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietro.getScene().getWindow();
        currentStage.close();
    }

}
