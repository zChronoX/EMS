package classi;

import interfacce.GeneratoreCredenziali;

import java.util.Date;
import java.util.Objects;

public class Docente extends Utente implements GeneratoreCredenziali {

    private String codiceDocente;
    private String Password;

   /* public Docente(String nome, String cognome, String genere, Date data_nascita, String codice_fiscale, String residenza, String email, String telefono, TipoProfilo tipoProfilo, String codiceDocente, String password) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.codiceDocente = codiceDocente;
        Password = password;
    }*/
    //public Docente(){};

    //USATO
   /*public Docente(TipoProfilo tipoProfilo) {
       this.tipoProfilo = tipoProfilo;
   }*/
    public Docente(
            String nome,
            String cognome,
            String genere,
            Date data_nascita,
            String codice_fiscale,
            String residenza,
            String email,
            String telefono,
            TipoProfilo tipoProfilo,
            String codiceDocente,
            String password
    ) {
        super(nome, cognome, genere, data_nascita, codice_fiscale, residenza, email, telefono, tipoProfilo);
        this.codiceDocente = codiceDocente;
        Password = password;
    }

    public Docente(TipoProfilo tipoProfilo) {
        super(tipoProfilo); // Chiama il costruttore della superclasse Utente
    }

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
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", tipoProfilo=" + tipoProfilo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docente docente = (Docente) o;
        return Objects.equals(codiceDocente, docente.codiceDocente); // Confronta SOLO codiceDocente
    }
    @Override
    public int hashCode() {
        return Objects.hash(codiceDocente, Password);
    }
}
