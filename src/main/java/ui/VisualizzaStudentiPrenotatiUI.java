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

public class VisualizzaStudentiPrenotatiUI implements Initializable {
    private Appello_esame appello;
    private Insegnamento insegnamento;
    private EMS ems;
    private Docente docente;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        docente = ems.getDocenteCorrente();
        insegnamento=ems.getInsegnamentoSelezionato();
        appello=ems.getAppelloCorrente();
        inserisciEsitiButton.setVisible(true);
        visualizzaStudenti();

    }

    public VisualizzaStudentiPrenotatiUI() {}

    @FXML
    private ListView<String> studentiPrenotatiListView;

    @FXML
    private Button inserisciEsitiButton;

    @FXML
    private Button IndietroButton;




    private void visualizzaStudenti() {
        studentiPrenotatiListView.getItems().clear();
        if (appello != null) {
            List<Studente> studenti = ems.getStudentiByAppello();

            if (studenti == null || studenti.isEmpty()) {
                studentiPrenotatiListView.getItems().add("Non ci sono studenti prenotati a questo appello.");
                inserisciEsitiButton.setVisible(false);
                return;
            }

            // Cancella gli elementi esistenti prima di aggiungere i nuovi
            studentiPrenotatiListView.getItems().clear();

            for (Studente studente : studenti) {
                studentiPrenotatiListView.getItems().add(studente.getNome() + " " + studente.getCognome() + " (" + studente.getMatricola() + ")");
            }
        }
    }

    @FXML
    private void apriPopUpInserimentoEsito(ActionEvent event) throws IOException {
        // 1. Carica il pop-up
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InserisciEsitiPopUpView.fxml"));
        Parent root = fxmlLoader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Impedisce l'interazione con altre finestre
        popupStage.setScene(new Scene(root));
        popupStage.setTitle("Inserisci Esito");
        popupStage.showAndWait();
        visualizzaStudenti();
    }

    @FXML
    public void IndietroAppelliDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaAppelliInsegnamentoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) IndietroButton.getScene().getWindow();
        currentStage.close();
    }

}
