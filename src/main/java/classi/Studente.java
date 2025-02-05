package classi;

import interfacce.GeneratoreCredenziali;

import java.util.Date;

public class Studente extends Utente implements GeneratoreCredenziali {
    private String Matricola;
    private String Password;
    private String Categoria;
    private int AnnoCorso;

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
