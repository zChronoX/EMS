package ui;

import classi.EMS;
import classi.Studente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CancellaStudenteUI implements Initializable {

    public CancellaStudenteUI() {}

    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroWelcomeView.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void cancellaStudente() throws IOException {
        String matricola = matricolaField.getText();

        try {
            Studente studente = ems.getStudenti().get(matricola); // recupera lo studente dalla mappa

            if (studente == null) {
                mostraMessaggio("Errore", "Studente non trovato.");
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
                    "Genere: " + studente.getGenere() + "\n" +
                    "Codice Fiscale: " + studente.getCodice_fiscale() + "\n" +
                    "Residenza: " + studente.getResidenza() + "\n" +
                    "Email: " + studente.getEmail() + "\n" +
                    "Telefono: " + studente.getTelefono() + "\n" +
                    "Categoria: " + studente.getCategoria() + "\n" +
                    "Anno di corso: " + studente.getAnnoCorso() + "\n";

            alert.setContentText(resoconto);

            ButtonType buttonTypeSi = new ButtonType("Si");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeSi) {
                    try {
                        ems.cancellaStudente(matricola);
                        mostraMessaggio("Successo", "Studente cancellato con successo.");
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
