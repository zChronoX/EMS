package ui;

import classi.EMS;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaFeedbackEsameUI implements Initializable {
    public VisualizzaFeedbackEsameUI() {}
    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }

}
