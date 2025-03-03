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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizzaAppelliPerModificaUI implements Initializable {
    private EMS ems;
    private Insegnamento insegnamento;
    private List<Appello_esame> appelli;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        insegnamento=ems.getInsegnamentoSelezionato();
        visualizzaAppelli();
    }

    @FXML
    private TextArea appelliTextArea;

    @FXML
    private TextField idAppelloTextField;

    @FXML
    private Button indietroButton;

    @FXML
    private Button selezionaAppelloButton;


    private void visualizzaAppelli() {
        if (insegnamento != null) {
            appelli = ems.getAppelliByInsegnamento();
            if (appelli != null && !appelli.isEmpty()) {
                String appelliFormattati = formattaAppelli(appelli);
                appelliTextArea.setText(appelliFormattati);
            } else {
                appelliTextArea.setText("Non ci sono appelli per questo insegnamento.");
            }
        } else {
            appelliTextArea.setText("Insegnamento non trovato."); // Gestisce il caso in cui 'insegnamento' è null
        }
    }

    private String formattaAppelli(List<Appello_esame> appelli) {
        StringBuilder sb = new StringBuilder();
        for (Appello_esame appello : appelli) {
            String appelloFormattato = String.format(
                    "ID: %s Orario: %s  Data: %s  Luogo: %s\n",
                    appello.getID_appello(),
                    appello.getOrario(),
                    appello.getData(),
                    appello.getLuogo()
            );
            sb.append(appelloFormattato);
        }
        return sb.toString();
    }

    @FXML
    public void Indietro() throws IOException {
        Stage primaryStage = (Stage) indietroButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModificaAppelloView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Modifica Appello");
    }

    @FXML
    public void selezionaAppello(){
        String idAppello = idAppelloTextField.getText(); // Recupera l'ID dal TextField

        if (idAppello != null && !idAppello.isEmpty()) { // Verifica se l'ID è stato inserito
            System.out.println("ID Appello inserito: " + idAppello);

            // ricerca l'appello nella lista
            for (Appello_esame appello : appelli) {
                if (appello.getID_appello().equals(idAppello)) {
                    System.out.println("Appello trovato: " + appello.getID_appello());

                    //voglio modificare l'appello trovato
                    ems.setAppelloCorrente(appello);

                    try {
                        reinserisciDatiAppello();
                    } catch (IOException e) {
                        // Gestisci l'eccezione in caso di errore nell'apertura della finestra
                        e.printStackTrace(); // Stampa l'errore per debug
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore durante l'apertura della finestra");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }

                    return; // Esce dal metodo dopo aver trovato l'appello
                }
            }
        }else {
            // ID non inserito, mostra un messaggio di errore
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inserisci un ID di appello.");
            alert.showAndWait();
        }
    }

    void reinserisciDatiAppello() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReinserisciDatiAppelloView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Reinserisci Dati Appello");
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) selezionaAppelloButton.getScene().getWindow();
        currentStage.close();
    }
}
