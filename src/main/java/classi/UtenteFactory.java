package classi;

import java.util.Date;
import interfacce.GeneratoreCredenziali;

public class UtenteFactory {
    //fixme forse da togliere
    public static Utente creaUtente(Utente.TipoProfilo tipo, String Nome, String Cognome, String Genere, Date Data_nascita, String Codice_fiscale, String Residenza,String Email, String Telefono, String Categoria, int AnnoCorso, GeneratoreCredenziali generatoreCredenziali) {
        GeneratoreCredenziali generatore = new GeneratoreCredenziali() {
            @Override
            public String generaMatricola() {
                return GeneratoreCredenziali.super.generaMatricola();
            }

            @Override
            public String generaPassword() {
                return GeneratoreCredenziali.super.generaPassword();
            }
            @Override
            public String generaCodiceDocente() {
                return GeneratoreCredenziali.super.generaCodiceDocente();
            }

        };

        switch (tipo) {
            case Studente:
                return new Studente(Nome, Cognome, Genere, Data_nascita, Codice_fiscale, Residenza, Email, Telefono, Utente.TipoProfilo.Studente, generatore.generaMatricola(), generatore.generaPassword(), Categoria, AnnoCorso);
            case Docente:
                return new Docente(Nome, Cognome, Genere, Data_nascita, Codice_fiscale, Residenza,Email, Telefono, Utente.TipoProfilo.Docente, generatore.generaCodiceDocente(), generatore.generaPassword());
            default:
                throw new IllegalArgumentException("Tipo di utente non valido: " + tipo);
        }
    }
    public Utente newUser(Utente.TipoProfilo tipo) {
        if(tipo == Utente.TipoProfilo.Studente) {

            return new Studente();
        } else if (tipo == Utente.TipoProfilo.Docente) {
            return new Docente();
        }
        else return null;
    }
}
