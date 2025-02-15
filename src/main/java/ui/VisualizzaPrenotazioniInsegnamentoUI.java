package ui;

import classi.EMS;
import classi.Insegnamento;
import classi.Studente;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VisualizzaPrenotazioniInsegnamentoUI implements Initializable {
    private EMS ems; // Istanza di EMS
    private Studente studente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        visualizzaInsegnamenti();
        studente = ems.getStudenteCorrente();
        System.out.println("UIPrenotazioneAppello: Studente: " + studente); // Stampa l'oggetto Studente
    }

    public VisualizzaPrenotazioniInsegnamentoUI() {}

    @FXML
    private ListView<String> insegnamentiListView;

    @FXML
    private TextField codiceInsegnamentoTextField;

    @FXML
    private Button cercaInsegnamentoButton;

    @FXML
    private Button BottoneIndietroVistaStudente;



    private void visualizzaInsegnamenti() {
        if (ems != null) {
            HashMap<String, Insegnamento> insegnamenti = ems.getInsegnamenti();
            if (insegnamenti != null) {
                for (Insegnamento insegnamento : insegnamenti.values()) {
                    insegnamentiListView.getItems().add(insegnamento.getID_insegnamento() + " - " + insegnamento.getNome());
                }
            }
        }
    }
    @FXML
    private void confermaCodiceInsegnamento(ActionEvent event) throws IOException {
        String codiceInsegnamento = codiceInsegnamentoTextField.getText();

        Insegnamento insegnamento = ems.getInsegnamento(codiceInsegnamento);

        if (insegnamento != null) {
            //FORSE MANCAVA QUESTO SET --> FUNZIONA ADESSO
            ems.setInsegnamentoSelezionato(insegnamento);

            apriListaAppelliView(insegnamento);
        } else {
            // Gestisci il caso in cui l'insegnamento non viene trovato
            System.out.println("Insegnamento non trovato.");
            // Puoi anche mostrare un messaggio di errore all'utente qui, ad esempio:
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insegnamento non trovato.");
            alert.showAndWait();
        }
    }

    private void apriListaAppelliView(Insegnamento insegnamento) throws IOException {
        Stage primaryStage = (Stage) codiceInsegnamentoTextField.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotazioniAppelloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Appelli di " + insegnamento.getNome());
    }

    @FXML
    public void IndietroCercaVistaStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Pagina Studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroVistaStudente.getScene().getWindow();
        currentStage.close();
    }
}
