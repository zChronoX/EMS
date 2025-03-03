package classi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EMSTest {

    static EMS ems;
    static UtenteFactory utenteFactory;
    @BeforeAll
    public static void initTest() {
        ems = EMS.getInstance();
        utenteFactory = new UtenteFactory();

    }

    @Test
    void TestAggiungiInfoStudente() {
        try {
            Studente studente = new Studente();
            studente.setTipoProfilo(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);


            ems.AggiungiInfoStudente("In corso", 2023);


            assertEquals("In corso", studente.getCategoria());
            assertEquals(2023, studente.getAnnoCorso());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void TestCreaProfiloUtente_Studente() {

        try {
        Utente studente = utenteFactory.newUser(Utente.TipoProfilo.Studente);
        ems.setUtenteCorrente(studente);

        String nome = "Mario";
        String cognome = "Rossi";
        Date dataNascita = new Date();
        String genere = "Maschio";
        String codiceFiscale = "RSSMRO80A01L219X";
        String residenza = "Milano";
        String email = "mario.rossi@example.com";
        String telefono = "1234567890";


        boolean risultato = ems.creaProfiloUtente(nome, cognome, dataNascita, genere, codiceFiscale, residenza, email, telefono);


        assertTrue(risultato);
        assertEquals(nome, studente.getNome());
        assertEquals(cognome, studente.getCognome());
        assertEquals(dataNascita, studente.getData_nascita());
        assertEquals(genere, studente.getGenere());
        assertEquals(codiceFiscale, studente.getCodice_fiscale());
        assertEquals(residenza, studente.getResidenza());
        assertEquals(email, studente.getEmail());
        assertEquals(telefono, studente.getTelefono());
    } catch (Exception e) {
        fail("Unexpected exception: " + e.getMessage());}
    }


    @Test
    void TestCreaProfiloUtente_Docente() {

        try {
            Utente docente = utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

            String nome = "Luigi";
            String cognome = "Verdi";
            Date dataNascita = new Date();
            String genere = "Maschio";
            String codiceFiscale = "VRDLGU70A01F205E";
            String residenza = "Roma";
            String email = "luigi.verdi@example.com";
            String telefono = "9876543210";


            boolean risultato = ems.creaProfiloUtente(nome, cognome, dataNascita, genere, codiceFiscale, residenza, email, telefono);


            assertTrue(risultato);
            assertEquals(nome, docente.getNome());
            assertEquals(cognome, docente.getCognome());
            assertEquals(dataNascita, docente.getData_nascita());
            assertEquals(genere, docente.getGenere());
            assertEquals(codiceFiscale, docente.getCodice_fiscale());
            assertEquals(residenza, docente.getResidenza());
            assertEquals(email, docente.getEmail());
            assertEquals(telefono, docente.getTelefono());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());}
    }

    @Test
    void testGeneraCredenziali_Studente() {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);


            ems.generaCredenziali();


            assertNotNull(studente.getMatricola());
            assertNotNull(studente.getPassword());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGeneraCredenziali_Docente() {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);


            ems.generaCredenziali();


            assertNotNull(docente.getCodiceDocente());
            assertNotNull(docente.getPassword());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testScegliTipoProfilo_Studente() {
        try {

            ems.scegliTipoProfilo(Utente.TipoProfilo.Studente);


            assertNotNull(ems.getUtenteCorrente());
            assertTrue(ems.getUtenteCorrente() instanceof Studente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testScegliTipoProfilo_Docente() {
        try {

            ems.scegliTipoProfilo(Utente.TipoProfilo.Docente);


            assertNotNull(ems.getUtenteCorrente());
            assertTrue(ems.getUtenteCorrente() instanceof Docente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testConfermaUtente_Studente() {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            studente.setNome("Mario");
            studente.setCognome("Rossi");
            studente.setData_nascita(new Date());
            studente.setGenere("Maschio");
            studente.setCodice_fiscale("RSSMRO80A01L219X");
            studente.setResidenza("Milano");
            studente.setEmail("mario.rossi@example.com");
            studente.setTelefono("1234567890");


            studente.setMatricola("12345");
            studente.setPassword("password123");
            studente.setCategoria("In corso");
            studente.setAnnoCorso(2023);



            ems.confermaUtente();


            assertNotNull(ems.getStudenti().get("12345"));
            assertEquals(studente, ems.getStudenti().get("12345"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testConfermaUtente_Docente() {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
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


            assertNotNull(ems.getDoc_list().get("DV987"));
            assertEquals(docente, ems.getDoc_list().get("DV987"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testVerificaCodiceFiscaleEsistente_Docente() {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

            docente.setNome("Mario");
            docente.setCognome("Rossi");
            docente.setData_nascita(new Date());
            docente.setGenere("Maschio");
            docente.setCodice_fiscale("RSSMRO80A01L735A");
            docente.setResidenza("Milano");
            docente.setEmail("mario.rossi@example.com");
            docente.setTelefono("1234567890");
            docente.setCodiceDocente("CD123");
            docente.setPassword("password123");

            ems.confermaUtente();


            boolean esiste = ems.verificaCodiceFiscaleEsistente("RSSMRO80A01L735A");


            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testVerificaCodiceFiscaleEsistente_Studente() {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

            studente.setNome("Luigi");
            studente.setCognome("Verdi");
            studente.setData_nascita(new Date());
            studente.setGenere("Maschio");
            studente.setCodice_fiscale("VRDLGU90B02M908T");
            studente.setResidenza("Roma");
            studente.setEmail("luigi.verdi@example.com");
            studente.setTelefono("9876543210");
            studente.setMatricola("12345");
            studente.setPassword("password456");
            studente.setCategoria("In corso");
            studente.setAnnoCorso(2023);

            ems.confermaUtente();


            boolean esiste = ems.verificaCodiceFiscaleEsistente("VRDLGU90B02M908T");


            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }




    @Test
    void testLoginStudente() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente);


            boolean risultato = ems.loginStudente("12345", "password123");


            assertTrue(risultato);
            assertEquals(studente, ems.getStudenteCorrente());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoginStudente_StudenteNonPresente() {
        try {

            assertThrows(Exception.class, () -> {
                ems.loginStudente("2345", "password123");
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoginStudente_PasswordErrata() {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente);


            assertThrows(Exception.class, () -> {
                ems.loginStudente("12345", "password_errata");
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }




    @Test
    void loginDocente() {
        try {
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");
            ems.getDoc_list().put("DV987", docente);


            boolean risultato = ems.loginDocente("DV987", "password456");


            assertTrue(risultato);
            assertEquals(docente, ems.getDocenteCorrente());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testLoginDocente_DocenteNonPresente() {
        try {

            assertThrows(Exception.class, () -> {
                ems.loginDocente("DV987", "password456");
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoginDocente_PasswordErrata() {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");
            ems.getDoc_list().put("DV987", docente);


            assertThrows(Exception.class, () -> {
                ems.loginDocente("DV987", "password_errata");
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCreazioneAppello_Successo() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);


            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";


            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);


            assertNotNull(idAppello);
            assertNull(insegnamento.getExam_list().get(idAppello));


            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            ems.setAppelloCorrente(appello);


            ems.confermaAppello();


            assertNotNull(insegnamento.getExam_list().get(idAppello));
            assertEquals(insegnamento, insegnamento.getExam_list().get(idAppello).getInsegnamento());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }



    @Test
    void testCreazioneAppello_InsegnamentoNonTrovato() {
        try {

            ems.setInsegnamentoSelezionato(null);


            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";


            assertThrows(IllegalArgumentException.class, () -> {
                ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCreazioneAppello_AppelloEsistente() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);


            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);


            insegnamento.aggiungiAppello(appelloEsistente);


            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);


            assertNull(idAppello);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCreazioneAppello_DataOraLuogoUguali() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);


            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);


            insegnamento.aggiungiAppello(appelloEsistente);


            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);


            assertNull(idAppello);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testConfermaAppello_Successo() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";


            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);


            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);


            ems.setAppelloCorrente(appello);


            ems.confermaAppello();


            assertNotNull(insegnamento.getExam_list().get(idAppello));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetAppelli_AppelliPresenti() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento1 = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento1);

            Insegnamento insegnamento2 = new Insegnamento("MAT-01", "Matematica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("MAT-01", insegnamento2);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello1 = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento1);
            insegnamento1.aggiungiAppello(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", data, ora, "Aula B", 30, "Orale", insegnamento2);
            insegnamento2.aggiungiAppello(appello2);


            studente.getAppelli().add(appello1);
            studente.getAppelli().add(appello2);


            List<Appello_esame> appelliPrenotati = ems.getAppelli();


            assertEquals(2, appelliPrenotati.size());
            assertTrue(appelliPrenotati.contains(appello1));
            assertTrue(appelliPrenotati.contains(appello2));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }



    @Test
    void testGetAppelliByInsegnamento_AppelliPresenti() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            Appello_esame appello1 = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", data, orario, "Aula B", postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello2);


            List<Appello_esame> appelli = ems.getAppelliByInsegnamento();


            assertEquals(2, appelli.size());
            assertTrue(appelli.contains(appello1));
            assertTrue(appelli.contains(appello2));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetAppelloById_IdValido() {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);


            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            Appello_esame appello = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello);


            Appello_esame appelloRecuperato = ems.getAppelloById("APP-1");


            assertNotNull(appelloRecuperato);
            assertEquals(appello, appelloRecuperato);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCancellaStudente_Successo() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente);


            boolean result = ems.cancellaStudente("12345");


            assertTrue(result);
            assertNull(ems.getStudenti().get("12345"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGeneraIdPrenotazione_FormatoCorretto() {
        try {

            String idPrenotazione = ems.returnGeneraIdPrenotazione();


            assertNotNull(idPrenotazione);
            assertEquals(6, idPrenotazione.length());
            assertTrue(idPrenotazione.matches("\\d+"));


            int idValue = Integer.parseInt(idPrenotazione);
            assertTrue(idValue >= 100000 && idValue <= 999999);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Test
    void testIsStudentePrenotato() {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);

            appello.getStudenti().add(studente);


            boolean isPrenotato = ems.isStudentePrenotato(studente, appello);


            assertTrue(isPrenotato);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testPrenotaAppello() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);


            boolean prenotazioneRiuscita = ems.prenotaAppello(appello);


            assertTrue(prenotazioneRiuscita);
            assertTrue(appello.getStudenti().contains(studente));
            assertTrue(studente.getAppelli().contains(appello));
            assertEquals(29, appello.getPostiDisponibili());


            boolean prenotazioneTrovata = false;
            for (Prenotazione p : ems.getReservation_list().values()) {
                if (p.getStudente().equals(studente) && p.getAppello().equals(appello)) {
                    prenotazioneTrovata = true;
                    break;
                }
            }
            assertTrue(prenotazioneTrovata);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testCancellaPrenotazione() throws Exception {
        try {
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);

            ems.prenotaAppello(appello);


            ems.cancellaPrenotazione(appello);


            assertFalse(appello.getStudenti().contains(studente));
            assertFalse(studente.getAppelli().contains(appello));
            assertEquals(30, appello.getPostiDisponibili());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetPrenotazioneByStudenteAndAppello_PrenotazioneEsistente() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            ems.setAppelloCorrente(appello);

            ems.prenotaAppello(appello);


            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();


            assertNotNull(prenotazione);
            assertEquals(studente, prenotazione.getStudente());
            assertEquals(appello, prenotazione.getAppello());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testInserisciEsito_Successo() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello);

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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
            ems.prenotaAppello(appello);


            ems.inserisciEsito("12345", "25", "Approvato");


            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();
            assertNotNull(prenotazione.getEsito());
            assertEquals("25", prenotazione.getEsito().getVoto());
            assertEquals("Approvato", prenotazione.getEsito().getStato());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testRifiutaEsito_Successo() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello);

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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
            ems.prenotaAppello(appello);


            ems.inserisciEsito("12345", "25", "Approvato");


            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();
            ems.rifiutaEsito(prenotazione.getEsito());


            assertEquals("Rifiutato", prenotazione.getEsito().getStato());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testVisualizzaAppelliPerInsegnamento_AppelliEsistenti() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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

            Insegnamento insegnamento1 = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            insegnamento1.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento1);

            Insegnamento insegnamento2 = new Insegnamento("MAT-01", "Matematica", 6, "Descrizione", 2023);
            insegnamento2.aggiungiDocente(docente);
            ems.getTeaching_list().put("MAT-01", insegnamento2);

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento1);
            insegnamento1.aggiungiAppello(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Orale", insegnamento1);
            insegnamento1.aggiungiAppello(appello2);

            Appello_esame appello3 = new Appello_esame("APP-3", LocalDate.now(), LocalTime.now(), "Aula C", 30, "Scritto", insegnamento2);
            insegnamento2.aggiungiAppello(appello3);


            ems.setInsegnamentoSelezionato(insegnamento1);


            List<Appello_esame> appelli = ems.getAppelliByInsegnamento();


            assertEquals(2, appelli.size());
            assertTrue(appelli.contains(appello1));
            assertTrue(appelli.contains(appello2));
            assertFalse(appelli.contains(appello3));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testGetStudentiByAppello_AppelloConStudenti() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello);

            Studente studente1 = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente1);

            studente1.setNome("Mario");
            studente1.setCognome("Rossi");
            studente1.setData_nascita(new Date());
            studente1.setGenere("Maschio");
            studente1.setCodice_fiscale("RSSMRO80A01L735A");
            studente1.setResidenza("Milano");
            studente1.setEmail("mario.rossi@example.com");
            studente1.setTelefono("1234567890");
            studente1.setMatricola("12345");
            studente1.setPassword("password123");
            studente1.setCategoria("In corso");
            studente1.setAnnoCorso(2023);
            ems.confermaUtente();
            ems.prenotaAppello(appello);

            Studente studente2 = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente2);

            studente2.setNome("Luigi");
            studente2.setCognome("Verdi");
            studente2.setData_nascita(new Date());
            studente2.setGenere("Maschio");
            studente2.setCodice_fiscale("VRDLGU70A01F205E");
            studente2.setResidenza("Roma");
            studente2.setEmail("luigi.verdi@example.com");
            studente2.setTelefono("9876543210");
            studente2.setMatricola("67890");
            studente2.setPassword("password456");
            studente2.setCategoria("Fuori corso");
            studente2.setAnnoCorso(2020);
            ems.confermaUtente();
            ems.prenotaAppello(appello);


            List<Studente> studenti = ems.getStudentiByAppello();


            assertEquals(2, studenti.size());
            assertTrue(studenti.contains(studente1));
            assertTrue(studenti.contains(studente2));

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetStudente_StudenteEsistente() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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


            Studente retrievedStudente = ems.getStudente("12345");


            assertNotNull(retrievedStudente);
            assertEquals("Mario", retrievedStudente.getNome());
            assertEquals("Rossi", retrievedStudente.getCognome());
            assertEquals("12345", retrievedStudente.getMatricola());
            assertEquals("RSSMRO80A01L735A", retrievedStudente.getCodice_fiscale());
            assertEquals("Milano", retrievedStudente.getResidenza());
            assertEquals("mario.rossi@example.com", retrievedStudente.getEmail());
            assertEquals("1234567890", retrievedStudente.getTelefono());
            assertEquals("password123", retrievedStudente.getPassword());
            assertEquals("In corso", retrievedStudente.getCategoria());
            assertEquals(2023, retrievedStudente.getAnnoCorso());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCalcolaMediaVoti_StudenteConVoti() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Scritto", insegnamento);

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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
            ems.prenotaAppello(appello1);
            ems.prenotaAppello(appello2);


            ems.setAppelloCorrente(appello1);
            ems.inserisciEsito("12345", "25", "Approvato");

            ems.setAppelloCorrente(appello2);
            ems.inserisciEsito("12345", "30", "Approvato");


            double media = ems.calcolaMediaVoti(studente);


            assertEquals(27.5, media);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testControlloEsistenzaAppello_AppelloEsistente() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";

            Appello_esame appello = new Appello_esame("APP-1", data, orario, luogo, 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);


            ems.setInsegnamentoSelezionato(insegnamento);


            boolean esiste = ems.controlloEsistenzaAppello(data, orario, luogo);


            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetPrenotazioniNonRecensiteByStudente_PrenotazioniNonRecensite() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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
            insegnamento.aggiungiDocente(docente);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Scritto", insegnamento);

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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
            ems.prenotaAppello(appello1);
            ems.prenotaAppello(appello2);


            HashMap<String, Prenotazione> prenotazioni = ems.getPrenotazioniNonRecensiteByStudente();


            assertEquals(2, prenotazioni.size());
            assertTrue(prenotazioni.containsKey(ems.getReservation_list().entrySet().stream().filter(entry -> entry.getValue().getStudente().equals(studente) && entry.getValue().getAppello().equals(appello1)).findFirst().orElseThrow().getKey()));
            assertTrue(prenotazioni.containsKey(ems.getReservation_list().entrySet().stream().filter(entry -> entry.getValue().getStudente().equals(studente) && entry.getValue().getAppello().equals(appello2)).findFirst().orElseThrow().getKey()));

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testGetAppelliApprovati_AppelliApprovati() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento1 = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento1);

            Insegnamento insegnamento2 = new Insegnamento("MAT-01", "Matematica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("MAT-01", insegnamento2);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello1 = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento1);
            insegnamento1.aggiungiAppello(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", data, ora, "Aula B", 30, "Orale", insegnamento2);
            insegnamento2.aggiungiAppello(appello2);


            Prenotazione prenotazione1 = new Prenotazione("PREN-1", data, ora, 1, studente, appello1);
            Esito_esame esito1 = new Esito_esame("28", "Approvato", studente, appello1);
            prenotazione1.setEsito(esito1);
            ems.getReservation_list().put("PREN-1", prenotazione1);

            Prenotazione prenotazione2 = new Prenotazione("PREN-2", data, ora, 2, studente, appello2);
            Esito_esame esito2 = new Esito_esame("25", "Approvato", studente, appello2);
            prenotazione2.setEsito(esito2);
            ems.getReservation_list().put("PREN-2", prenotazione2);


            Prenotazione prenotazione3 = new Prenotazione("PREN-3", data, ora, 3, studente, appello1);
            Esito_esame esito3 = new Esito_esame("15", "Non Approvato", studente, appello1);
            prenotazione3.setEsito(esito3);
            ems.getReservation_list().put("PREN-3", prenotazione3);


            List<String> appelliApprovati = ems.getAppelliApprovati();


            assertEquals(2, appelliApprovati.size());
            assertTrue(appelliApprovati.contains("ID Appello: (APP-1) Nome Insegnamento: Informatica - Voto: 28 Esito: Approvato"));
            assertTrue(appelliApprovati.contains("ID Appello: (APP-2) Nome Insegnamento: Matematica - Voto: 25 Esito: Approvato"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testReinserisciDatiAppello_Successo() throws Exception {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate dataOriginale = LocalDate.now();
            LocalTime orarioOriginale = LocalTime.now();
            String luogoOriginale = "Aula A";

            Appello_esame appello = new Appello_esame("APP-1", dataOriginale, orarioOriginale, luogoOriginale, 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);


            ems.setAppelloCorrente(appello);


            LocalDate dataNuova = LocalDate.now().plusDays(1);
            LocalTime orarioNuovo = LocalTime.now().plusHours(1);
            String luogoNuovo = "Aula B";

            ems.reinserisciDatiAppello(dataNuova, orarioNuovo, luogoNuovo);


            assertEquals(dataNuova, appello.getData());
            assertEquals(orarioNuovo, appello.getOrario());
            assertEquals(luogoNuovo, appello.getLuogo());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testAggiungiFeedback_Successo() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            Prenotazione prenotazione = new Prenotazione("PREN-1", data, ora, 1, studente, appello);
            ems.getReservation_list().put("PREN-1", prenotazione);


            ems.setPrenotazioneCorrente(prenotazione);


            Optional<String> feedback = Optional.of("Ottimo appello!");
            ems.aggiungiFeedback(feedback);


            assertEquals(1, appello.getFeedbacks().size());
            assertEquals("Ottimo appello!", appello.getFeedbacks().get(0));
            assertTrue(prenotazione.getRecensito());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testGetFeedback_Successo() throws Exception {
        try {

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);


            appello.addFeedback("Ottimo appello!");
            appello.addFeedback("Ben organizzato.");


            ems.setAppelloCorrente(appello);


            List<String> feedbacks = ems.getFeedback();


            assertEquals(2, feedbacks.size());
            assertTrue(feedbacks.contains("Ottimo appello!"));
            assertTrue(feedbacks.contains("Ben organizzato."));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testModificaProfilo_Studente() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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


            String residenzaNuova = "Roma";
            String emailNuova = "mario.rossi.nuovo@example.com";
            String telefonoNuovo = "9876543210";

            ems.modificaProfilo(residenzaNuova, emailNuova, telefonoNuovo);


            assertEquals(residenzaNuova, studente.getResidenza());
            assertEquals(emailNuova, studente.getEmail());
            assertEquals(telefonoNuovo, studente.getTelefono());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testModificaProfilo_Docente() throws Exception {
        try {

            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

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


            String residenzaNuova = "Milano";
            String emailNuova = "luigi.verdi.nuovo@example.com";
            String telefonoNuovo = "1234567890";

            ems.modificaProfilo(residenzaNuova, emailNuova, telefonoNuovo);


            assertEquals(residenzaNuova, docente.getResidenza());
            assertEquals(emailNuova, docente.getEmail());
            assertEquals(telefonoNuovo, docente.getTelefono());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testIsTroppoTardiPerCancellare() throws Exception {
        try {

            LocalDate dataAppello = LocalDate.now().plusDays(2);
            Appello_esame appello = new Appello_esame("APP-1", dataAppello, LocalTime.now(), "Aula A", 30, "Scritto", null);


            boolean troppoTardi = ems.isTroppoTardiPerCancellare(appello);


            assertTrue(troppoTardi);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testHaRicevutoEsito_EsitoPresente() throws Exception {
        try {

            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

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

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            Prenotazione prenotazione = new Prenotazione("PREN-1", data, ora, 1, studente, appello);
            Esito_esame esito = new Esito_esame("28", "Approvato", studente, appello);
            prenotazione.setEsito(esito);
            ems.getReservation_list().put("PREN-1", prenotazione);


            boolean esitoPresente = ems.haRicevutoEsito(appello);


            assertTrue(esitoPresente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}












