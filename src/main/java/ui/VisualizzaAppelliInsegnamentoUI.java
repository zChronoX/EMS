package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VisualizzaAppelliInsegnamentoUI {

    @FXML
    private ListView<String> appelliInsegnamentoListView;

    @FXML
    private Button BottoneIndietroInsegnamentiDocente;

    private Insegnamento insegnamento;
    private Docente docente;
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = ems;
        docente = ems.getDocenteCorrente();
    }

    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento;
        visualizzaAppelli();
    }



    private void visualizzaAppelli() {
        if (insegnamento != null) {
            List<Appello_esame> appelli = ems.getAppelliByInsegnamento(insegnamento); // Implementa questo metodo in EMS

            if (appelli == null || appelli.isEmpty()) {
                appelliInsegnamentoListView.getItems().add("Non ci sono appelli per questo insegnamento.");
                return;
            }

            for (Appello_esame appello : appelli) {
                appelliInsegnamentoListView.getItems().add(appello.toString()); // Assumi che Appello_esame abbia un metodo toString()
            }
        }
    }

    @FXML
    public void IndietroInsegnamentiDocente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisualizzaPrenotatiEsameView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        VisualizzaPrenotatiEsameUI controller = fxmlLoader.getController();
        controller.setEMS(ems);
        stage.setTitle("Visualizza prenotati esame");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroInsegnamentiDocente.getScene().getWindow();
        currentStage.close();
    }
}
