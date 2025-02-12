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

public class VisualizzaStudentiPrenotatiUI {

    public VisualizzaStudentiPrenotatiUI() {}

    @FXML
    private ListView<String> studentiPrenotatiListView;

    @FXML
    private Button inserisciEsitiButton;

    @FXML
    private Button IndietroButton;

    private Appello_esame appello;
    private Insegnamento insegnamento;
    private EMS ems;
    private Docente docente;

    public void setEMS(EMS ems) {
        this.ems = ems;
        docente = ems.getDocenteCorrente();
    }
    public void setAppello(Appello_esame appello) {
        this.appello = appello;
        visualizzaStudenti();
    }
    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento; // Inizializza il campo insegnamento
    }


    private void visualizzaStudenti() {
        if (appello != null) {
            List<Studente> studenti = ems.getStudentiByAppello(appello);

            if (studenti == null || studenti.isEmpty()) {
                studentiPrenotatiListView.getItems().add("Non ci sono studenti prenotati a questo appello.");
                return;
            }

            // Cancella gli elementi esistenti prima di aggiungere i nuovi
            studentiPrenotatiListView.getItems().clear(); // <--- Aggiungi questa linea

            for (Studente studente : studenti) {
                studentiPrenotatiListView.getItems().add(studente.getNome() + " " + studente.getCognome() + " (" + studente.getMatricola() + ")");
            }
        }
    }

    @FXML
    private void apriPopUpInserimentoEsito(ActionEvent event) throws IOException {
        // 1. Carica il pop-up FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InserisciEsitiPopUpView.fxml"));
        Parent root = fxmlLoader.load();

        // 2. Ottieni il controller del pop-up
        InserisciEsitiPopUpUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        controller.setAppello(appello);

        // 3. Crea una nuova finestra (Stage) per il pop-up
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Impedisce l'interazione con altre finestre
        popupStage.setScene(new Scene(root));
        popupStage.setTitle("Inserisci Esito");

        // 4. Mostra il pop-up e attendi la chiusura
        popupStage.showAndWait();

        // 5. Aggiorna la lista degli studenti (opzionale)
        visualizzaStudenti();
    }

    @FXML
    public void IndietroAppelliDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaAppelliInsegnamentoView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaAppelliInsegnamentoUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        controller.setInsegnamento(insegnamento);
        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) IndietroButton.getScene().getWindow();
        currentStage.close();
    }

}
