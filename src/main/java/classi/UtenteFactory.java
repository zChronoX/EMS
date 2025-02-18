package classi;

import java.util.Date;
import interfacce.GeneratoreCredenziali;

public class UtenteFactory {

    public Utente newUser(Utente.TipoProfilo tipo) {
        if(tipo == Utente.TipoProfilo.Studente) {

            //return new Studente();
            return new Studente(tipo); // Passa il tipo al costruttore
        } else if (tipo == Utente.TipoProfilo.Docente) {
            return new Docente(tipo);
        }
        else return null;
    }
}
