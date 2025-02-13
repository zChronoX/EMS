package ui;

import classi.EMS;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GestisciEsitiUI implements Initializable {
    public GestisciEsitiUI() {}
    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }




}
