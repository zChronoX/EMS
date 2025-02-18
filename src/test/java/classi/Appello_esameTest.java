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
    void testVerificaDisponibilitaAppello() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appello, studente, and confirm them
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Set docente details
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
            appello.setTipologia("In corso"); // Imposta la tipologia dell'appello
            ems.getExam_list().put("APP-1", appello);

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Set studente details
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
            studente.setCategoria("In corso"); // Imposta la categoria dello studente
            studente.setAnnoCorso(2023);
            ems.confermaUtente();

            // 2. Execution: Check availability
            boolean disponibile = appello.verificaDisponibilitaAppello(studente);

            // 3. Assertions: Verify that the method returns true
            assertTrue(disponibile);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testAddStudente() throws Exception {
        try {
            // 1. Setup: Create docente, appello
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Set docente details
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
            ems.getExam_list().put("APP-1", appello);

            // 2. Setup: Create studente
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Set studente details
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

            // 3. Execution: Add studente to appello
            appello.addStudente(studente);

            // 4. Assertions: Verify that the studente is added
            assertTrue(appello.getStudenti().contains(studente));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testAddFeedback() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appello
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Set docente details
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
            ems.getExam_list().put("APP-1", appello);

            // 2. Execution: Add feedback
            appello.addFeedback("Ottimo servizio!");

            // 3. Assertions: Verify that the feedback is added
            assertEquals(1, appello.getFeedbacks().size());
            assertEquals("Ottimo servizio!", appello.getFeedbacks().get(0));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testPuòGestireEsiti() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appello, and confirm them
            Docente docente1 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente1);
            // Set docente details
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
            ems.getExam_list().put("APP-1", appello);

            // 2. Execution: Check if the docente can manage results
            boolean puòGestire = appello.puòGestireEsiti(docente1);

            // 3. Assertions: Verify that the method returns true
            assertTrue(puòGestire);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}