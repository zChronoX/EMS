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

    private void visualizzaInformazioniInsegnamento() {
        if (insegnamento != null) {
            nomeInsegnamentoLabel.setText("Insegnamento: " + insegnamento.getNome());
            codiceInsegnamentoLabel.setText("Codice: " + insegnamento.getID_insegnamento());
            cfuInsegnamentoLabel.setText("CFU: " + insegnamento.getCFU());
        }
    }
    private void visualizzaAppelliPrenotati() {
        if (studente != null && insegnamento != null) {
            List<Appello_esame> appelliPrenotati = studente.getAppelli(); // Ottieni tutti gli appelli prenotati dallo studente

            appelliPrenotatiListView.getItems().clear(); // Pulisci la lista

            if (appelliPrenotati == null || appelliPrenotati.isEmpty()) {
                appelliPrenotatiListView.getItems().add("Non hai prenotazioni per questo insegnamento.");
                return;
            }

            for (Appello_esame appello : appelliPrenotati) {
                if (appello.getInsegnamento().equals(insegnamento)) { // Filtra per l'insegnamento corrente
                    appelliPrenotatiListView.getItems().add(appello.toString());
                }
            }
        }
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
