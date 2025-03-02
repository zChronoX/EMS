package ui;

import classi.Appello_esame;
import classi.EMS;
import classi.Insegnamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ReinserisciDatiAppelloUI implements Initializable {
    private EMS ems;
    private List<Appello_esame> appelli;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        appelli = ems.getAppelliByInsegnamento();

    }

    @FXML
    private DatePicker dataDatePicker;

    @FXML
    private TextField orarioTextField;

    @FXML
    private TextField luogoTextField;

    @FXML
    private Button indietroButton;

    @FXML
    private Button bottoneConfermaModifiche;

    @FXML
    void modificaCampi(){
        LocalDate data = dataDatePicker.getValue();
        String luogo = luogoTextField.getText();

        String orarioString = orarioTextField.getText();
        LocalTime orario = null;

        if (orarioString != null && !orarioString.isEmpty()) {
            try {
                // Formato specifico per l'input HH:MM
                orario = LocalTime.parse(orarioString, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Formato orario non valido (HH:MM).");
                alert.showAndWait();
                return;
            }
        } else {
            // Gestisce il caso in cui l'orario non è stato inserito
            Alert alert = new Alert(Alert.AlertType.WARNING, "L'orario non è stato inserito.");
            alert.showAndWait();
            return;
        }
        if (ems.controlloEsistenzaAppello(data, orario, luogo)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Esiste già un appello con gli stessi dati.");
            alert.showAndWait();
            return; // Interrompe la modifica se l'appello esiste già
        }

        ems.reinserisciDatiAppello(data, orario, luogo);

        visualizzaLista();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appello modificato con successo.");
        alert.showAndWait();

    }

    void visualizzaLista(){
            System.out.println("Lista dopo la modifica:");
        for(int i=0; i<appelli.size(); i++){
            System.out.println(appelli.get(i).toString());
        }

    }

    @FXML
    public void Indietro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizzaAppelliPerModificaView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Lista Appelli dell'Insegnamento");
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) bottoneConfermaModifiche.getScene().getWindow();
        currentStage.close();
    }
}
