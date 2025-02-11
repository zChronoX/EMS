package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class WelcomeController {
    public WelcomeController() {}

    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
        ems.stampa_utenti();
    }




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
    public void ApriCancellaStudenti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CancellaStudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        CancellaStudenteUI controller = fxmlLoader.getController();
        controller.setEMS(ems); // Pass EMS *before* showing the stage. This will trigger the displayStudentList() method.
        stage.setTitle("Cancella profilo studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneCancellaStudente.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void ApriVisualizzaStudenti() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaStudentiView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaStudentiUI controller = fxmlLoader.getController();
        controller.setEMS(ems); // Pass EMS *before* showing the stage. This will trigger the displayStudentList() method.
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
        VisualizzaDocentiUI controller = fxmlLoader.getController();
        controller.setEMS(ems); // Passa l'istanza di EMS
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
        UICreaUtente controller = fxmlLoader.getController();
        controller.setEMS(ems);
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

        UILogin controller = fxmlLoader.getController();
        controller.setEMS(ems); // Passa l'istanza di EMS

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
        UICreaAppello controller = fxmlLoader.getController();
        controller.setEMS(ems);
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
        UIModificaAppello controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Modifica Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneModificaAppello.getScene().getWindow();
        currentStage.close();
    }




}