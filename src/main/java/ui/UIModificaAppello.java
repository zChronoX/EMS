package ui;

import classi.EMS;
import com.almasb.fxgl.ui.UI;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UIModificaAppello implements Initializable {

    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }

    public UIModificaAppello() {}



}
