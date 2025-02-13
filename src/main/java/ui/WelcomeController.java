package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    public WelcomeController() {}

    private EMS ems;

    @FXML
    private Button BottoneCreaUtente;
    @FXML
    private Button BottoneLogin;
    @FXML
    private Button BottoneCreaAppello;
    @FXML
    private Button BottoneModificaAppello;
    @FXML
    private Button BottoneVisualizzaStudenti;
    @FXML
    private Button BottoneVisualizzaDocenti;
    @FXML
    private Button BottoneCancellaStudente;

    @FXML
    private TextArea studentListTextArea;

    @FXML
    public void ApriVisualizzaStudenti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaStudentiView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza Elenco Studenti");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaStudenti.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void ApriVisualizzaDocenti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaDocentiView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Visualizza Elenco Docenti");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneVisualizzaDocenti.getScene().getWindow(); // Assumi che tu abbia un bottone per questa azione
        currentStage.close();
    }

    @FXML
    public void ApriCreaUtente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreaUtenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Crea Utente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneCreaUtente.getScene().getWindow();
        currentStage.close(); // Chiude WelcomeView
    }
    @FXML
    public void ApriLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneLogin.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriCreaAppello() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreaAppelloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Crea Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneCreaAppello.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriModificaAppello() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModificaAppelloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Modifica Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneModificaAppello.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriCancellaStudenti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CancellaStudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Cancella profilo studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneCancellaStudente.getScene().getWindow();
        currentStage.close();
    }
}