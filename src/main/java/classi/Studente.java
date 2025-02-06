package classi;

import interfacce.GeneratoreCredenziali;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Studente extends Utente implements GeneratoreCredenziali {
    private String Matricola;
    private String Password;
    private String Categoria;
    private int AnnoCorso;
    private List<Appello_esame> appelliPrenotati = new ArrayList<>();


    public void aggiungiAppelloPrenotato(Appello_esame appello) {
        this.appelliPrenotati.add(appello);
    }

    public List<Appello_esame> getAppelliPrenotati() {
        return appelliPrenotati;
    }

    {
        this.Matricola = generaMatricola();
        this.Password = generaPassword();
    }


    public Studente(String nome, String cognome, String genere, Date data_nascita, String codice_fiscale, String residenza, String email, String telefono, TipoProfilo tipoProfilo, String matricola, String password, String categoria, int annoCorso) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.Categoria = categoria;
        this.AnnoCorso = annoCorso;
    }
    public Studente(){};

    public String getMatricola() {
        return Matricola;
    }

    public void setMatricola(String matricola) {
        Matricola = matricola;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public int getAnnoCorso() {
        return AnnoCorso;
    }

    public void setAnnoCorso(int annoCorso) {
        AnnoCorso = annoCorso;
    }

    public void assegnaIdentificativiStudente() {
        this.setMatricola(generaMatricola());
        this.setPassword(generaPassword());
    }

    @Override
    public String generaMatricola() {
        return GeneratoreCredenziali.super.generaMatricola();
    }

    @Override
    public String generaPassword() {
        return GeneratoreCredenziali.super.generaPassword();
    }

    @Override
    public String toString() {
        return "Studente{" +
                "Matricola='" + Matricola + '\'' +
                ", Password='" + Password + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", AnnoCorso=" + AnnoCorso +
                ", Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Genere='" + Genere + '\'' +
                ", Data_nascita=" + Data_nascita +
                ", Codice_fiscale='" + Codice_fiscale + '\'' +
                ", Residenza='" + Residenza + '\'' +
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", tipoProfilo=" + tipoProfilo +
                '}';
    }
}
