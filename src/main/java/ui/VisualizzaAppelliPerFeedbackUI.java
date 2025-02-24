package ui;

import classi.Appello_esame;
import classi.Docente;
import classi.EMS;
import classi.Insegnamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class VisualizzaAppelliPerFeedbackUI implements Initializable {
    private EMS ems;
    private Docente docente;
    private Insegnamento insegnamento;
    private List<String> feedbacks;

    @FXML
    private ListView<String> appelliInsegnamentoListView;
    @FXML
    private TextField idAppelloTextField;
    @FXML
    private ListView<String> feedbackListView;
    @FXML
    private Button scegliAppelloButton;
    @FXML
    private Button BottoneIndietroInsegnamentiDocente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems= EMS.getInstance();
        docente = ems.getDocenteCorrente();
        insegnamento=ems.getInsegnamentoSelezionato();
        visualizzaAppelli();
    }

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
    private void visualizzaFeedback(){
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

        ems.setAppelloCorrente(appello);

        if(feedbackListView.getItems() != null || !feedbackListView.getItems().isEmpty()){
            feedbackListView.getItems().clear();
        }

        feedbacks=ems.getFeedback();
//        feedbacks=appello.getFeedbacks();
        if (feedbacks.isEmpty()) {
            feedbackListView.getItems().add("Non ci sono feedback per questo appello");
        } else {
//
            for (String feedback : feedbacks) {
                feedbackListView.getItems().add(feedback);
            }
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
}
