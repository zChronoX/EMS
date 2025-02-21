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
import java.util.*;

public class VisualizzaAppelliInsegnamentoUI implements Initializable {
    private EMS ems;
    private Insegnamento insegnamento;
    private Docente docente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        docente = ems.getDocenteCorrente();
        insegnamento=ems.getInsegnamentoSelezionato();
        visualizzaAppelli();
    }

    @FXML
    private ListView<String> appelliInsegnamentoListView;

    @FXML
    private Button BottoneIndietroInsegnamentiDocente;

    @FXML
    private TextField idAppelloTextField;

    @FXML
    private Button scegliAppelloButton;


    private void visualizzaAppelli() {
        if (insegnamento != null) {
            List<Appello_esame> exam_list = ems.getAppelliByInsegnamento();

            if (exam_list == null || exam_list.isEmpty()) {
                appelliInsegnamentoListView.getItems().add("Non ci sono appelli per questo insegnamento.");
                return;
            }

            for (Appello_esame appello : exam_list) {
                String appelloString = appello.getID_appello() + " - " + appello.getData();
                appelliInsegnamentoListView.getItems().add(appelloString);
            }
        }
    }
    @FXML
    private void visualizzaStudentiPrenotati() throws IOException {
        String idAppello = idAppelloTextField.getText();

        if (idAppello == null || idAppello.isEmpty()) {
            showAlert("Errore", "Inserisci l'ID dell'appello.");
            return;
        }
//
        Appello_esame appello = ems.getAppelloById(idAppello);

        if (appello == null) {
            showAlert("Errore", "Appello non trovato.");
            return;
        }
        ems.setAppelloCorrente(appello);
        System.out.println(" APPELLO CORRENTE: " + idAppello);
        System.out.println("AGGIORNAMENTO APPELLO CORRENTE: " + appello.getID_appello());
        apriListaStudentiView(appello, insegnamento);
    }

    private void apriListaStudentiView(Appello_esame appello, Insegnamento insegnamento) throws IOException {
        Stage primaryStage = (Stage) idAppelloTextField.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaStudentiPrenotatiView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Studenti prenotati all'appello " + appello.getID_appello());
    }

    @FXML
    public void IndietroInsegnamentiDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotatiEsameView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroInsegnamentiDocente.getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}