package ui;

import classi.Appello_esame;
import classi.EMS;
import classi.Insegnamento;
import com.almasb.fxgl.ui.UI;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class UIModificaAppello implements Initializable {

    private EMS ems;
    private HashMap<String, Insegnamento> insegnamenti;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        insegnamenti=ems.getInsegnamenti();
        visualizzaInsegnamenti();
    }

    public UIModificaAppello() {}

    @FXML
    private TextArea elencoInsegnamentiTextArea;

    @FXML
    private TextField codiceInsegnamentoTextField;

    @FXML
    private Button selezionaInsegnamentoButton;

    @FXML
    private Button indietroButton;

    private void visualizzaInsegnamenti() {
        if (insegnamenti != null && !insegnamenti.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (HashMap.Entry<String, Insegnamento> entry : insegnamenti.entrySet()) {
                Insegnamento insegnamento = entry.getValue();
                sb.append("Codice: ").append(insegnamento.getID_insegnamento()).append(", ");
                sb.append("Nome: ").append(insegnamento.getNome());

                sb.append("\n"); // Vado a capo dopo ogni insegnamento
            }
            elencoInsegnamentiTextArea.setText(sb.toString());
        } else {
            elencoInsegnamentiTextArea.setText("Nessun insegnamento trovato.");
        }
    }

    @FXML
    private void selezionaInsegnamento() {
        // 1. Recupera il codice dell'insegnamento inserito
        String codiceInsegnamento = codiceInsegnamentoTextField.getText();

        // 2. Verifica se il campo è vuoto
        if (codiceInsegnamento == null || codiceInsegnamento.isEmpty()) {
            mostraAvviso("Inserisci il codice dell'insegnamento.");
            return; // Esci dal metodo se il campo è vuoto
        }

        // 3. Cerca l'insegnamento nella HashMap
        Insegnamento insegnamentoSelezionato = insegnamenti.get(codiceInsegnamento);

        // 4. Verifica se l'insegnamento è stato trovato
        if (insegnamentoSelezionato != null) {
            // 5. Imposta l'insegnamento selezionato nell'EMS
            ems.setInsegnamentoSelezionato(insegnamentoSelezionato);
            System.out.println("Insegnamento selezionato: " + insegnamentoSelezionato.getNome());

            // 6. Apri la finestra per visualizzare gli appelli
            try {
                apriFinestraVisualizzaAppelli();
            } catch (IOException e) {
                // 7. Gestisci l'eccezione in caso di errore nell'apertura della finestra
                e.printStackTrace(); // Stampa l'errore per debug
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore durante l'apertura della finestra");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            mostraAvviso("Insegnamento non trovato.");
        }
    }

    private void mostraAvviso(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avviso");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void apriFinestraVisualizzaAppelli() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizzaAppelliPerModificaView.fxml"));
        Parent root = loader.load(); // Carica il file FXML e ottieni la radice della scena

        Stage stage = new Stage();
        stage.setTitle("Lista Appelli dell'Insegnamento");
        stage.setScene(new Scene(root)); // Imposta la scena con la radice caricata
        stage.show();

        // Opzionale: Chiudi la finestra corrente (se lo desideri)
        Stage currentStage = (Stage) selezionaInsegnamentoButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void Indietro() throws IOException {
        Stage primaryStage = (Stage) indietroButton.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }

}
