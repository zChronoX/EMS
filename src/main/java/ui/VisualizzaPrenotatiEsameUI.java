package ui;

import classi.Docente;
import classi.EMS;
import classi.Insegnamento;
import javafx.event.ActionEvent;
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
import java.util.List;
import java.util.ResourceBundle;

public class VisualizzaPrenotatiEsameUI implements Initializable {
    private EMS ems;
    private Docente docente;

    @FXML
    private ListView<String> insegnamentiDocenteListView;

    @FXML
    private TextField codiceInsegnamentoTextField;

    @FXML
    private Button scegliInsegnamentoButton;

    @FXML
    private Button BottoneIndietroPaginaDocente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        docente = ems.getDocenteCorrente();
        visualizzaInsegnamentiDocente();
    }

    private void visualizzaInsegnamentiDocente() {
        System.out.println("Docente: " + docente);
        //String codiceDocente = docente.getCodiceDocente();
        if (docente != null) {
            List<Insegnamento> insegnamenti = ems.mostraInsegnamentiDocente();

            if (insegnamenti == null || insegnamenti.isEmpty()) {
                insegnamentiDocenteListView.getItems().add("Non hai insegnamenti assegnati.");
                return;
            }

            for (Insegnamento insegnamento : insegnamenti) {
                insegnamentiDocenteListView.getItems().add(insegnamento.getID_insegnamento() + " - " + insegnamento.getNome());
            }
        }
    }

    public VisualizzaPrenotatiEsameUI() {}
/*
    @FXML
    private Button BottoneIndietroPrenotatiEsame;

 */

    @FXML
    private void visualizzaAppelliInsegnamento() throws IOException {
        String codiceInsegnamento = codiceInsegnamentoTextField.getText();

        if (codiceInsegnamento == null || codiceInsegnamento.isEmpty()) {
            showAlert("Errore", "Inserisci il codice dell'insegnamento.");
            return;
        }
        //serve per recuperare dalla lista in codice dell'insegnamento selezionato per poi settarlo
        Insegnamento insegnamento = ems.getInsegnamento(codiceInsegnamento);

        if (insegnamento == null) {
            showAlert("Errore", "Insegnamento non trovato.");
            return;
        }

        ems.setInsegnamentoSelezionato(insegnamento);

        // Pulisce il campo di testo dopo la selezione
        codiceInsegnamentoTextField.clear();

        apriListaAppelliView(insegnamento);
    }

    private void apriListaAppelliView(Insegnamento insegnamento) throws IOException {
        Stage primaryStage = (Stage) codiceInsegnamentoTextField.getScene().getWindow(); // Ottieni lo Stage corrente

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaAppelliInsegnamentoView.fxml")); // Crea un nuovo FXMLLoader
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene); // Imposta la nuova scena sullo Stage
        primaryStage.setTitle("Appelli di " + insegnamento.getNome());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void IndietroPaginaDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocenteView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Pagina Docente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroPaginaDocente.getScene().getWindow();
        currentStage.close();
    }
}
