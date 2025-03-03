package classi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Appello_esameTest {


    static EMS ems;
    static UtenteFactory utenteFactory;
    @BeforeAll
    public static void initTest() {
        ems = EMS.getInstance();
        utenteFactory = new UtenteFactory();

    }


    @Test
    void testAddStudente() throws Exception {
        try {
            // Setup
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Set docente
            docente.setNome("Luigi");
            docente.setCognome("Verdi");
            docente.setData_nascita(new Date());
            docente.setGenere("Maschio");
            docente.setCodice_fiscale("VRDLGU70A01F205E");
            docente.setResidenza("Roma");
            docente.setEmail("luigi.verdi@example.com");
            docente.setTelefono("9876543210");
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");
            ems.confermaUtente();

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            insegnamento.aggiungiDocente(docente); // Associa il docente all'insegnamento
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            // insegnamento.aggiungiAppello() per aggiungere l'appello all'insegnamento
            insegnamento.aggiungiAppello(appello);

            // Setup
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Set studente
            studente.setNome("Mario");
            studente.setCognome("Rossi");
            studente.setData_nascita(new Date());
            studente.setGenere("Maschio");
            studente.setCodice_fiscale("RSSMRO80A01L735A");
            studente.setResidenza("Milano");
            studente.setEmail("mario.rossi@example.com");
            studente.setTelefono("1234567890");
            studente.setMatricola("12345");
            studente.setPassword("password123");
            studente.setCategoria("In corso");
            studente.setAnnoCorso(2023);
            ems.confermaUtente();


            appello.addStudente(studente);


            assertTrue(appello.getStudenti().contains(studente));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testAddFeedback() throws Exception {
        try {
            // Setup
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Set docente
            docente.setNome("Luigi");
            docente.setCognome("Verdi");
            docente.setData_nascita(new Date());
            docente.setGenere("Maschio");
            docente.setCodice_fiscale("VRDLGU70A01F205E");
            docente.setResidenza("Roma");
            docente.setEmail("luigi.verdi@example.com");
            docente.setTelefono("9876543210");
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");
            ems.confermaUtente();

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            insegnamento.aggiungiDocente(docente); // Associa il docente all'insegnamento
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            // insegnamento.aggiungiAppello() per aggiungere l'appello all'insegnamento
            insegnamento.aggiungiAppello(appello);


            appello.addFeedback("Ottimo esame!");


            assertEquals(1, appello.getFeedbacks().size());
            assertEquals("Ottimo esame!", appello.getFeedbacks().get(0));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testPuòGestireEsiti() throws Exception {
        try {
            // Setup
            Docente docente1 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente1);
            // Set docente
            docente1.setNome("Luigi");
            docente1.setCognome("Verdi");
            docente1.setData_nascita(new Date());
            docente1.setGenere("Maschio");
            docente1.setCodice_fiscale("VRDLGU70A01F205E");
            docente1.setResidenza("Roma");
            docente1.setEmail("luigi.verdi@example.com");
            docente1.setTelefono("9876543210");
            docente1.setCodiceDocente("DV987");
            docente1.setPassword("password456");
            ems.confermaUtente();

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            insegnamento.aggiungiDocente(docente1); // Associa il docente all'insegnamento
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            // insegnamento.aggiungiAppello() per aggiungere l'appello all'insegnamento
            insegnamento.aggiungiAppello(appello);


            boolean puòGestire = appello.puòGestireEsiti(docente1);


            assertTrue(puòGestire);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}