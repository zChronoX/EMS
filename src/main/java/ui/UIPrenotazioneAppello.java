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

public class UIPrenotazioneAppello implements Initializable {
    public UIPrenotazioneAppello() {}

    private EMS ems; // Istanza di EMS
    private Studente studente;
    private Insegnamento insegnamento;


    @FXML
    private ListView<String> insegnamentiListView;

    @FXML
    private TextField codiceInsegnamentoTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        visualizzaInsegnamenti();
        studente = ems.getStudenteCorrente();
        System.out.println("UIPrenotazioneAppello: Studente: " + studente.getNome() + " " + studente.getCognome() + " " + studente.getMatricola() + "\n\n" ); // Stampa l'oggetto Studente
    }


@FXML
private void visualizzaInsegnamenti() {
    HashMap<String, Insegnamento> insegnamenti = ems.getInsegnamenti(); // Recupera la mappa degli insegnamenti

    if (insegnamenti != null) {
        for (Map.Entry<String, Insegnamento> entry : insegnamenti.entrySet()) {
            Insegnamento insegnamento = entry.getValue();
            String insegnamentoString = insegnamento.getID_insegnamento() + " - " + insegnamento.getNome();
            insegnamentiListView.getItems().add(insegnamentoString);
        }
    }
}

    @FXML
    private void confermaCodiceInsegnamento(ActionEvent event) throws IOException {
        String codiceInsegnamento = codiceInsegnamentoTextField.getText();

        insegnamento = ems.getInsegnamento(codiceInsegnamento);

        if (insegnamento != null) {
            ems.setInsegnamentoSelezionato(insegnamento);
            apriListaAppelliView(insegnamento);
        } else {
            // Gestisci il caso in cui l'insegnamento non viene trovato
            System.out.println("Insegnamento non trovato.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insegnamento non trovato.");
            alert.showAndWait();
        }
    }

    @FXML
    private void apriListaAppelliView(Insegnamento insegnamento) throws IOException {
        Stage primaryStage = (Stage) codiceInsegnamentoTextField.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListaAppelliView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        primaryStage.setScene(scene);
        primaryStage.setTitle("Appelli di " + insegnamento.getNome());
    }

    @FXML
    private Button BottoneIndietroVistaStudente;

    @FXML
    public void IndietroCercaVistaStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Pagina Studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroVistaStudente.getScene().getWindow();
        currentStage.close();
    }
}
