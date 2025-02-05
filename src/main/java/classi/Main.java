package classi;

import java.util.Date;
import interfacce.GeneratoreCredenziali;
public class Main {
    public static void main(String[] args) {
        GeneratoreCredenziali generatore = new GeneratoreCredenziali(){};

        Studente s = new Studente("Giovanni", "Contarino", "Maschile",
                new Date(), "CNTGNN01D07C351H",
                "Via Blanco 14, Acireale",
                "giovanni.contarino.gc@gmail.com", "3801577024",
                Utente.TipoProfilo.Studente, generatore.generaMatricola(), generatore.generaPassword()
                , "In corso", 2025);

        Docente d = new Docente("Nappo", "Giuseppe", "Indefinito",
                new Date(), "NPPGGPS214312PP", "Via Scaloto 12, Scala",
                "nappopippo@gmail.com", "1234567890", Utente.TipoProfilo.Docente, generatore.generaCodiceDocente(),
                generatore.generaPassword());


        System.out.println(d);
        System.out.println(s);

    }
}