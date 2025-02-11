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
    private List<Appello_esame> appelli = new ArrayList<>();

    {
        this.Matricola = generaMatricola();
        this.Password = generaPassword();
    }

    public void addAppello(Appello_esame appello) throws Exception {
        if (appello == null) {
            throw new Exception("Appello non valido.");
        }
        if (this.appelli.contains(appello)) {
            throw new Exception("Appello già presente nella lista.");
        }
        this.appelli.add(appello);
    }

    public void removeAppello(Appello_esame appello) {
        this.appelli.remove(appello);
    }

    public List<Appello_esame> getAppelli() {
        return appelli;
    }



    /*public Studente(String nome, String cognome, String genere, Date data_nascita, String codice_fiscale, String residenza, String email, String telefono, TipoProfilo tipoProfilo, String matricola, String password, String categoria, int annoCorso) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.Categoria = categoria;
        this.AnnoCorso = annoCorso;
    }*/
   // public Studente(){};

    //USARE
   // public Studente(TipoProfilo tipoProfilo) {
    //    this.tipoProfilo = tipoProfilo;
    //}

   /* // Costruttore per caricamento studenti statici (tutti i campi)
    public Studente(String nome, String cognome, String genere, Date data_nascita, String codice_fiscale, String residenza, String email, String telefono, TipoProfilo tipoProfilo, String categoria, int annoCorso) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.Categoria = categoria;
        this.AnnoCorso = annoCorso;
        this.Matricola = generaMatricola();
        this.Password = generaPassword();
    }*/

    // Costruttore per creazione utente da controller (solo tipoProfilo inizialmente)
    public Studente(TipoProfilo tipoProfilo) {
        super(tipoProfilo); // Chiama il costruttore di Utente che accetta solo tipoProfilo
        // Gli altri campi (Matricola, Password, Categoria, AnnoCorso)
        // verranno impostati successivamente tramite i setter.
    }
    public Studente(
            String nome,
            String cognome,
            String genere,
            Date data_nascita,
            String codice_fiscale,
            String residenza,
            String email,
            String telefono,
            TipoProfilo tipoProfilo,
            String matricola,
            String password,
            String categoria,
            int annoCorso
    ) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.Matricola = matricola;
        this.Password = password;
        this.Categoria = categoria;
        this.AnnoCorso = annoCorso;
    }


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
        //a chi sto assegnando questi valori?? CHI E' THIS???


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
