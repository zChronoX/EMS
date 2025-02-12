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

public class VisualizzaAppelliInsegnamentoUI {

    @FXML
    private ListView<String> appelliInsegnamentoListView;

    @FXML
    private Button BottoneIndietroInsegnamentiDocente;

    @FXML
    private TextField idAppelloTextField;

    @FXML
    private Button scegliAppelloButton;

    private Insegnamento insegnamento;
    private Docente docente;
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = ems;
        docente = ems.getDocenteCorrente();
    }

    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento;
        visualizzaAppelli();
    }



    private void visualizzaAppelli() {
        if (insegnamento != null) {
            List<Appello_esame> appelli = ems.getAppelliByInsegnamento(insegnamento); // Implementa questo metodo in EMS

            if (appelli == null || appelli.isEmpty()) {
                appelliInsegnamentoListView.getItems().add("Non ci sono appelli per questo insegnamento.");
                return;
            }

            for (Appello_esame appello : appelli) {
                appelliInsegnamentoListView.getItems().add(appello.toString()); // Assumi che Appello_esame abbia un metodo toString()
            }
        }
    }

    @FXML
    private void visualizzaStudentiPrenotati(ActionEvent event) throws IOException {
        String idAppello = idAppelloTextField.getText();

        if (idAppello == null || idAppello.isEmpty()) {
            showAlert("Errore", "Inserisci l'ID dell'appello.");
            return;
        }

        Appello_esame appello = ems.getAppelloById(idAppello); // Implementa questo metodo in EMS, accetta una stringa

        if (appello == null) {
            showAlert("Errore", "Appello non trovato.");
            return;
        }

        apriListaStudentiView(appello, insegnamento);
    }

    private void apriListaStudentiView(Appello_esame appello, Insegnamento insegnamento) throws IOException {
        Stage primaryStage = (Stage) idAppelloTextField.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaStudentiPrenotatiView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        VisualizzaStudentiPrenotatiUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        controller.setAppello(appello);
        controller.setInsegnamento(insegnamento); // Passa anche l'insegnamento
        primaryStage.setScene(scene);
        primaryStage.setTitle("Studenti prenotati all'appello " + appello.getID_appello());
    }


    @FXML
    public void IndietroInsegnamentiDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotatiEsameView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaPrenotatiEsameUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
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
