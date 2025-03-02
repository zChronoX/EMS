package ui;

import classi.EMS;
import classi.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UIStudente implements Initializable {

    public UIStudente() { }

    private EMS ems; // Istanza di EMS
    private Studente studente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        studente=ems.getStudenteCorrente();
        ems.setUtenteCorrente(studente);
        visualizzaInformazioniStudente();
    }

    @FXML
    private Button BottonePrenotazioneStudente;
    @FXML
    private Button BottoneVisualizzaCarriera;

    @FXML
    private Button BottoneVisualizzaInfoEsame;

    @FXML
    private Button BottoneInviaFeedback;
    @FXML
    private Button BottoneModificaProfilo;
    @FXML
    private Button BottoneLogoutStudente;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label matricolaLabel;

    private void visualizzaInformazioniStudente() {
        if (studente != null) {
            nomeLabel.setText("Nome: " + studente.getNome());
            cognomeLabel.setText("Cognome: " + studente.getCognome());
            matricolaLabel.setText("Matricola: " + studente.getMatricola());

        }
    }

    @FXML
    public void ApriPrenotazioneStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrenotazioneAppelloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottonePrenotazioneStudente.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriVisualizzaInfoEsame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotazioniInsegnamentoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza info Esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaInfoEsame.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriCarrieraStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaCarrieraView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Carriera Studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaCarriera.getScene().getWindow();
        currentStage.close();
    }


    @FXML
    public void LogoutStudente() throws IOException {
        Stage primaryStage = (Stage) BottoneLogoutStudente.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("EMS");
    }

    @FXML
    public void ApriModificaProfilo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModificaProfiloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Modifica Profilo");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneModificaProfilo.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriInviaFeedback() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InviaFeedbackView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Invia Feedback");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneInviaFeedback.getScene().getWindow();
        currentStage.close();
    }


}
