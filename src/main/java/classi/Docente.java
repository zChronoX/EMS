package classi;

import interfacce.GeneratoreCredenziali;

import java.util.Date;

public class Docente extends Utente implements GeneratoreCredenziali {

    private String codiceDocente;
    private String Password;

    public Docente(String nome, String cognome, String genere, Date data_nascita, String codice_fiscale, String residenza, String email, String telefono, TipoProfilo tipoProfilo, String codiceDocente, String password) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.codiceDocente = codiceDocente;
        Password = password;
    }
    public Docente(){};

    public String getCodiceDocente() {
        return codiceDocente;
    }

    public void setCodiceDocente(String codiceDocente) {
        this.codiceDocente = codiceDocente;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void assegnaIdentificativiDocente() {
        this.setCodiceDocente(generaCodiceDocente());
        this.setPassword(generaPassword());
    }


    @Override
    public String generaCodiceDocente() {
        return GeneratoreCredenziali.super.generaCodiceDocente();
    }

    @Override
    public String generaPassword() {
        return GeneratoreCredenziali.super.generaPassword();
    }
    @Override
    public String toString() {
        return "Docente{" +
                "codiceDocente='" + codiceDocente + '\'' +
                ", Password='" + Password + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Cognome='" + Cognome + '\'' +
                ", Genere='" + Genere + '\'' +
                ", Data_nascita=" + Data_nascita +
                ", Codice_fiscale='" + Codice_fiscale + '\'' +
                ", Residenza='" + Residenza + '\'' +
                ", Telefono='" + Telefono + '\'' +
                '}';
    }
}
