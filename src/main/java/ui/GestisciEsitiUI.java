package ui;

import classi.EMS;

public class GestisciEsitiUI {
    public GestisciEsitiUI() {}
    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }
}
