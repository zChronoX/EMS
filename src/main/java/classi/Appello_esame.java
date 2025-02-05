package classi;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appello_esame {
    private String ID_appello;
    private LocalTime Orario;
    private LocalDate Data;
    private String Luogo;
    private int postiDisponibili;
    private String Tipologia;

    public Appello_esame(String ID_appello, LocalTime orario, LocalDate data, String luogo,
                         int postiDisponibili, String tipologia) {
        this.ID_appello = ID_appello;
        Orario = orario;
        Data = data;
        Luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        Tipologia = tipologia;
    }


    public String getID_appello() {
        return ID_appello;
    }

    public void setID_appello(String ID_appello) {
        this.ID_appello = ID_appello;
    }

    public LocalTime getOrario() {
        return Orario;
    }

    public void setOrario(LocalTime orario) {
        Orario = orario;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public String getLuogo() {
        return Luogo;
    }

    public void setLuogo(String luogo) {
        Luogo = luogo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public String getTipologia() {
        return Tipologia;
    }

    public void setTipologia(String tipologia) {
        Tipologia = tipologia;
    }



    @Override
    public String toString() {
        return "Appello_esame{" +
                "ID_appello='" + ID_appello + '\'' +
                ", Orario=" + Orario +
                ", Data=" + Data +
                ", Luogo='" + Luogo + '\'' +
                ", postiDisponibili=" + postiDisponibili +
                ", Tipologia='" + Tipologia + '\'' +
                '}';
    }
}
