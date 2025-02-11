package ui;

import classi.EMS;
import classi.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CancellaStudenteUI {

    public CancellaStudenteUI() {}

    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }


    @FXML
    private TextField matricolaField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button BottoneIndietroWelcomeView;

    @FXML
    private Button cancellaButton;


    @FXML
    public void IndietroWelcomeView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroWelcomeView.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void cancellaStudente() throws IOException {
        String matricola = matricolaField.getText();
        String password = passwordField.getText();

        try {
            Studente studente = ems.getStudenti().get(matricola); // Ottieni lo studente dalla mappa

            if (studente == null) {
                mostraMessaggio("Errore", "Studente non trovato.");
                return;
            }

            if (!studente.getPassword().equals(password)) {
                mostraMessaggio("Errore", "Password errata.");
                return;
            }

            // Mostra popup di conferma
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Cancellazione");
            alert.setHeaderText("Cancellare lo studente?");
            String resoconto = "Matricola: " + studente.getMatricola() + "\n" +
                    "Nome: " + studente.getNome() + "\n" +
                    "Cognome: " + studente.getCognome() + "\n" +
                    "Data di nascita: " + studente.getData_nascita() + "\n" +
                    "Genere: " + studente.getGenere() + "\n" + // Aggiunto genere
                    "Codice Fiscale: " + studente.getCodice_fiscale() + "\n" + // Aggiunto codice fiscale
                    "Residenza: " + studente.getResidenza() + "\n" + // Aggiunta residenza
                    "Email: " + studente.getEmail() + "\n" + // Aggiunta email
                    "Telefono: " + studente.getTelefono() + "\n" + // Aggiunto telefono
                    "Categoria: " + studente.getCategoria() + "\n" +
                    "Anno di corso: " + studente.getAnnoCorso() + "\n";

            alert.setContentText(resoconto);

            ButtonType buttonTypeSi = new ButtonType("Si");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeSi) {
                    try {
                        ems.cancellaStudente(matricola, password);
                        mostraMessaggio("Successo", "Studente cancellato con successo.");
                        // Puoi anche chiudere la finestra o resettare i campi qui
                    } catch (Exception e) {
                        mostraMessaggio("Errore", "Errore durante la cancellazione: " + e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            mostraMessaggio("Errore", e.getMessage());
        }
    }

    private void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
