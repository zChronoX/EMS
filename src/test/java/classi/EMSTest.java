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
            ems.setUtenteCorrente(studente); // Usa l'istanza di EMS già creata

            // 2. Esecuzione: Chiama la funzione da testare
            ems.AggiungiInfoStudente("In corso", 2023);

            // 3. Asserzioni: Verifica che le informazioni siano state aggiunte correttamente
            assertEquals("In corso", studente.getCategoria());
            assertEquals(2023, studente.getAnnoCorso());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void TestCreaProfiloUtente_Studente() {
        // 1. Setup: Crea utente Studente con la factory e lo imposta come utente corrente
        try {
        Utente studente = utenteFactory.newUser(Utente.TipoProfilo.Studente);
        ems.setUtenteCorrente(studente);

        String nome = "Mario";
        String cognome = "Rossi";
        Date dataNascita = new Date(); // Usa un oggetto Date di prova
        String genere = "Maschio";
        String codiceFiscale = "RSSMRO80A01L219X";
        String residenza = "Milano";
        String email = "mario.rossi@example.com";
        String telefono = "1234567890";

        // 2. Esecuzione: Chiama la funzione da testare
        boolean risultato = ems.creaProfiloUtente(nome, cognome, dataNascita, genere, codiceFiscale, residenza, email, telefono);

        // 3. Asserzioni: Verifica che la funzione abbia restituito true e che i dati siano stati impostati correttamente
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
        // 1. Setup: Crea utente Studente con la factory e lo imposta come utente corrente
        try {
            Utente docente = utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

            String nome = "Luigi";
            String cognome = "Verdi";
            Date dataNascita = new Date(); // Usa un oggetto Date di prova
            String genere = "Maschio";
            String codiceFiscale = "VRDLGU70A01F205E";
            String residenza = "Roma";
            String email = "luigi.verdi@example.com";
            String telefono = "9876543210";

            // 2. Esecuzione: Chiama la funzione da testare
            boolean risultato = ems.creaProfiloUtente(nome, cognome, dataNascita, genere, codiceFiscale, residenza, email, telefono);

            // 3. Asserzioni: Verifica che la funzione abbia restituito true e che i dati siano stati impostati correttamente
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
            // 1. Setup: Crea utente Studente con la factory e lo imposta come utente corrente
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);

            // 2. Esecuzione: Chiama la funzione da testare
            ems.generaCredenziali();

            // 3. Asserzioni: Verifica che i dati specifici di Studente siano stati generati
            assertNotNull(studente.getMatricola()); // Verifica che la matricola sia stata generata
            assertNotNull(studente.getPassword()); // Verifica che la password sia stata generata
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGeneraCredenziali_Docente() {
        try {
            // 1. Setup: Crea utente Docente con la factory e lo imposta come utente corrente
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);

            // 2. Esecuzione: Chiama la funzione da testare
            ems.generaCredenziali();

            // 3. Asserzioni: Verifica che i dati specifici di Docente siano stati generati
            assertNotNull(docente.getCodiceDocente()); // Verifica che il codice docente sia stato generato
            assertNotNull(docente.getPassword()); // Verifica che la password sia stata generata
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testScegliTipoProfilo_Studente() {
        try {
            // 1. Esecuzione: Chiama la funzione da testare con tipo Studente
            ems.scegliTipoProfilo(Utente.TipoProfilo.Studente);

            // 2. Asserzioni: Verifica che utenteCorrente sia stato impostato correttamente
            assertNotNull(ems.getUtenteCorrente());
            assertTrue(ems.getUtenteCorrente() instanceof Studente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testScegliTipoProfilo_Docente() {
        try {
            // 1. Esecuzione: Chiama la funzione da testare con tipo Docente
            ems.scegliTipoProfilo(Utente.TipoProfilo.Docente);

            // 2. Asserzioni: Verifica che utenteCorrente sia stato impostato correttamente
            assertNotNull(ems.getUtenteCorrente());
            assertTrue(ems.getUtenteCorrente() instanceof Docente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testConfermaUtente_Studente() {
        try {
            // 1. Setup: Crea utente Studente con la factory, imposta i dati specifici e lo imposta come utente corrente
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            studente.setNome("Mario");
            studente.setCognome("Rossi");
            studente.setData_nascita(new Date()); // Usa un oggetto Date di prova
            studente.setGenere("Maschio");
            studente.setCodice_fiscale("RSSMRO80A01L219X");
            studente.setResidenza("Milano");
            studente.setEmail("mario.rossi@example.com");
            studente.setTelefono("1234567890");

            // Dati specifici per Studente
            studente.setMatricola("12345");
            studente.setPassword("password123");
            studente.setCategoria("In corso");
            studente.setAnnoCorso(2023);


            // 2. Esecuzione: Chiama la funzione da testare
            ems.confermaUtente();

            // 3. Asserzioni: Verifica che lo studente sia stato aggiunto alla lista di studenti in EMS
            assertNotNull(ems.getStudenti().get("12345")); // Verifica che lo studente sia presente nella mappa studenti
            assertEquals(studente, ems.getStudenti().get("12345")); // Verifica che l'oggetto nella mappa sia lo stesso
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testConfermaUtente_Docente() {
        try {
            // 1. Setup: Crea utente Docente con la factory, imposta i dati specifici e lo imposta come utente corrente
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            docente.setNome("Luigi");
            docente.setCognome("Verdi");
            docente.setData_nascita(new Date()); // Usa un oggetto Date di prova
            docente.setGenere("Maschio");
            docente.setCodice_fiscale("VRDLGU70A01F205E");
            docente.setResidenza("Roma");
            docente.setEmail("luigi.verdi@example.com");
            docente.setTelefono("9876543210");

            // Dati specifici per Docente
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");

            // 2. Esecuzione: Chiama la funzione da testare
            ems.confermaUtente();

            // 3. Asserzioni: Verifica che il docente sia stato aggiunto alla lista di docenti in EMS
            assertNotNull(ems.getDoc_list().get("DV987")); // Verifica che il docente sia presente nella mappa docenti
            assertEquals(docente, ems.getDoc_list().get("DV987")); // Verifica che l'oggetto nella mappa sia lo stesso
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testVerificaCodiceFiscaleEsistente_Docente() {
        try {
            // 1. Setup: Crea un docente con la factory, imposta i dati e lo conferma
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente); // Imposta come utente corrente
            // Dati docente (esempio)
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

            ems.confermaUtente(); // Conferma il docente

            // 2. Esecuzione: Verifica se il codice fiscale esiste
            boolean esiste = ems.verificaCodiceFiscaleEsistente("RSSMRO80A01L735A");

            // 3. Asserzioni: Verifica che la funzione restituisca true
            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testVerificaCodiceFiscaleEsistente_Studente() {
        try {
            // 1. Setup: Crea uno studente con la factory, imposta i dati e lo conferma
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente); // Imposta come utente corrente
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            // 2. Esecuzione: Verifica se il codice fiscale esiste
            boolean esiste = ems.verificaCodiceFiscaleEsistente("VRDLGU90B02M908T");

            // 3. Asserzioni: Verifica che la funzione restituisca true
            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }




    @Test
    void testLoginStudente() throws Exception {
        try {
            // 1. Setup: Crea uno studente e lo aggiungi alla lista
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente); // Aggiungi alla lista

            // 2. Esecuzione: Tenta il login
            boolean risultato = ems.loginStudente("12345", "password123");

            // 3. Asserzioni: Verifica che il login abbia avuto successo e che studenteCorrente sia stato impostato
            assertTrue(risultato);
            assertEquals(studente, ems.getStudenteCorrente());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoginStudente_StudenteNonPresente() {
        try {
            // 1. Setup: Non aggiungo lo studente alla lista

            // 2. Esecuzione: Tenta il login e verifica che venga lanciata l'eccezione
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
            // 1. Setup: Crea uno studente e lo aggiungi alla lista
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente);

            // 2. Esecuzione: Tenta il login con password errata e verifica che venga lanciata l'eccezione
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
            ems.getDoc_list().put("DV987", docente); // Aggiungi alla lista

            // 2. Esecuzione: Tenta il login
            boolean risultato = ems.loginDocente("DV987", "password456");

            // 3. Asserzioni: Verifica che il login abbia avuto successo e che docenteCorrente sia stato impostato
            assertTrue(risultato);
            assertEquals(docente, ems.getDocenteCorrente());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testLoginDocente_DocenteNonPresente() {
        try {
            // 1. Setup: Non aggiungo il docente alla lista

            // 2. Esecuzione: Tenta il login e verifica che venga lanciata l'eccezione
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
            // 1. Setup: Crea un docente e lo aggiungi alla lista
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            docente.setCodiceDocente("DV987");
            docente.setPassword("password456");
            ems.getDoc_list().put("DV987", docente);

            // 2. Esecuzione: Tenta il login con password errata e verifica che venga lanciata l'eccezione
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
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            // Dati appello
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare
            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);

            // 3. Asserzioni: Verifica che l'appello sia stato creato e aggiunto alle liste
            assertNotNull(idAppello);
            assertNull(insegnamento.getExam_list().get(idAppello)); // Verifica la mappa dell'insegnamento

            // 4. Imposta appello corrente (usando l'ID)
            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            ems.setAppelloCorrente(appello);

            // 5. Esecuzione: Conferma l'appello
            ems.confermaAppello();

            // 6. Asserzioni: Verifica che l'appello sia stato aggiunto a exam_list dopo confermaAppello
            assertNotNull(insegnamento.getExam_list().get(idAppello)); // Verifica la mappa dell'insegnamento
            assertEquals(insegnamento, insegnamento.getExam_list().get(idAppello).getInsegnamento());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }



    @Test
    void testCreazioneAppello_InsegnamentoNonTrovato() {
        try {
            // 1. Setup: Non aggiungo l'insegnamento alla lista

            // Imposta insegnamentoSelezionato a null
            ems.setInsegnamentoSelezionato(null);

            // Dati appello
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare e verifica che venga lanciata l'eccezione
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
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            // Crea un appello esistente
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);

            // Aggiungi l'appello alla mappa degli appelli dell'insegnamento
            insegnamento.aggiungiAppello(appelloEsistente);

            // 2. Esecuzione: Chiama la funzione da testare con gli stessi dati e verifica che venga restituito null
            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);

            // 3. Asserzioni: Verifica che la funzione abbia restituito null
            assertNull(idAppello);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCreazioneAppello_DataOraLuogoUguali() {
        try {
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            // Crea un appello esistente
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);

            // Aggiungi l'appello alla mappa degli appelli dell'insegnamento
            insegnamento.aggiungiAppello(appelloEsistente);

            // 2. Esecuzione: Chiama la funzione da testare con gli stessi dati
            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);

            // 3. Asserzioni: Verifica che la funzione restituisca null
            assertNull(idAppello);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testConfermaAppello_Successo() {
        try {
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare
            String idAppello = ems.creazioneAppello(data, orario, luogo, postiDisponibili, tipologia);

            // 3. Crea l'oggetto Appello_esame *direttamente* usando i dati
            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);

            // 4. Imposta appello corrente *con l'oggetto appena creato*
            ems.setAppelloCorrente(appello);

            // 5. Esecuzione: Conferma l'appello
            ems.confermaAppello();

            // 6. Asserzioni: Verifica che l'appello sia presente nella mappa exam_list dell'insegnamento
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
            // 1. Setup: Insegnamento esiste e ha appelli
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            Appello_esame appello1 = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello1); // Aggiungi appello all'insegnamento

            Appello_esame appello2 = new Appello_esame("APP-2", data, orario, "Aula B", postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello2); // Aggiungi appello all'insegnamento

            // 2. Esecuzione: Chiama la funzione
            List<Appello_esame> appelli = ems.getAppelliByInsegnamento();

            // 3. Asserzioni: Verifica che la lista contenga gli appelli corretti
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
            // 1. Setup: Crea un insegnamento e un appello
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            Appello_esame appello = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            insegnamento.aggiungiAppello(appello);

            // 2. Esecuzione: Chiama la funzione con un ID valido
            Appello_esame appelloRecuperato = ems.getAppelloById("APP-1");

            // 3. Asserzioni: Verifica che l'appello recuperato sia corretto
            assertNotNull(appelloRecuperato);
            assertEquals(appello, appelloRecuperato);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCancellaStudente_Successo() throws Exception {
        try {
            // 1. Setup: Create a student and add it to the list
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            studente.setMatricola("12345");
            studente.setPassword("password123");
            ems.getStudenti().put("12345", studente);

            // 2. Execution: Call the function
            boolean result = ems.cancellaStudente("12345");

            // 3. Assertions: Verify the student is removed and the function returns true
            assertTrue(result);
            assertNull(ems.getStudenti().get("12345")); // Check if the student is removed
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGeneraIdPrenotazione_FormatoCorretto() {
        try {
            // 1. Esecuzione: Genera un ID di prenotazione
            String idPrenotazione = ems.returnGeneraIdPrenotazione(); // Assumendo che generaIdPrenotazione sia un metodo statico di EMS

            // 2. Asserzioni: Verifica che l'ID sia nel formato corretto
            assertNotNull(idPrenotazione); // Verifica che l'ID non sia null
            assertEquals(6, idPrenotazione.length()); // Verifica che l'ID abbia 6 cifre
            assertTrue(idPrenotazione.matches("\\d+")); // Verifica che l'ID contenga solo numeri

            // Verifica che l'ID sia compreso tra 100000 e 999999 (opzionale, ma consigliato)
            int idValue = Integer.parseInt(idPrenotazione);
            assertTrue(idValue >= 100000 && idValue <= 999999);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Test
    void testIsStudentePrenotato() {
        try {
            // 1. Setup: Crea studente e appello con la factory, imposta i dati e conferma lo studente
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);

            appello.getStudenti().add(studente); // Aggiungi lo studente all'appello

            // 2. Esecuzione: Verifica se lo studente è prenotato
            boolean isPrenotato = ems.isStudentePrenotato(studente, appello);

            // 3. Asserzioni: Verifica che la funzione restituisca true
            assertTrue(isPrenotato);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testPrenotaAppello() throws Exception {
        try {
            // 1. Setup: Crea studente, appello e conferma lo studente
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);

            // 2. Esecuzione: Prenota l'appello
            boolean prenotazioneRiuscita = ems.prenotaAppello(appello);

            // 3. Asserzioni: Verifica che la prenotazione sia andata a buon fine
            assertTrue(prenotazioneRiuscita);
            assertTrue(appello.getStudenti().contains(studente));
            assertTrue(studente.getAppelli().contains(appello));
            assertEquals(29, appello.getPostiDisponibili());

            // Verifica che la prenotazione sia nella lista *cercando per studente e appello*
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

            ems.confermaUtente(); // Conferma lo studente

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);

            ems.prenotaAppello(appello);


            ems.cancellaPrenotazione(appello);


            assertFalse(appello.getStudenti().contains(studente)); // Studente non più presente nella lista dell'appello
            assertFalse(studente.getAppelli().contains(appello)); // Appello non più presente nella lista dello studente
            assertEquals(30, appello.getPostiDisponibili()); // Posti disponibili incrementati
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetPrenotazioneByStudenteAndAppello_PrenotazioneEsistente() throws Exception {
        try {
            // 1. Setup: Crea studente, appello, conferma lo studente e prenota l'appello
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            Appello_esame appello = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello); // Aggiungi appello all'insegnamento

            ems.setAppelloCorrente(appello); // Imposta appello corrente

            ems.prenotaAppello(appello); // Prenota l'appello

            // 2. Esecuzione: Recupera la prenotazione
            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();

            // 3. Asserzioni: Verifica che la prenotazione sia stata recuperata correttamente
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
            ems.setAppelloCorrente(appello);

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
            ems.prenotaAppello(appello);

            // 2. Execution: Insert the result
            ems.inserisciEsito("12345", "25", "Approvato");

            // 3. Assertions: Verify the result is inserted correctly
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
            ems.setAppelloCorrente(appello);

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
            ems.prenotaAppello(appello);

            // Inserisci un esito di prova (altrimenti non c'è nulla da rifiutare)
            ems.inserisciEsito("12345", "25", "Approvato");

            // 2. Execution: Rifiuta l'esito
            Prenotazione prenotazione = ems.getPrenotazioneByStudenteAndAppello();
            ems.rifiutaEsito(prenotazione.getEsito());

            // 3. Assertions: Verify the result is rejected
            assertEquals("Rifiutato", prenotazione.getEsito().getStato());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testVisualizzaAppelliPerInsegnamento_AppelliEsistenti() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appelli, and confirm them
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

            Insegnamento insegnamento1 = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            insegnamento1.aggiungiDocente(docente); // Associa il docente all'insegnamento
            ems.getTeaching_list().put("INF-01", insegnamento1);

            Insegnamento insegnamento2 = new Insegnamento("MAT-01", "Matematica", 6, "Descrizione", 2023);
            insegnamento2.aggiungiDocente(docente); // Associa il docente all'insegnamento
            ems.getTeaching_list().put("MAT-01", insegnamento2);

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento1);
            insegnamento1.aggiungiAppello(appello1); // Aggiungi appello all'insegnamento

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Orale", insegnamento1);
            insegnamento1.aggiungiAppello(appello2); // Aggiungi appello all'insegnamento

            Appello_esame appello3 = new Appello_esame("APP-3", LocalDate.now(), LocalTime.now(), "Aula C", 30, "Scritto", insegnamento2);
            insegnamento2.aggiungiAppello(appello3); // Aggiungi appello all'insegnamento

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento1);

            // 2. Execution: Get appelli for insegnamento1
            List<Appello_esame> appelli = ems.getAppelliByInsegnamento();

            // 3. Assertions: Verify the correct appelli are returned
            assertEquals(2, appelli.size());
            assertTrue(appelli.contains(appello1));
            assertTrue(appelli.contains(appello2));
            assertFalse(appelli.contains(appello3)); // Verifica che non ci sia l'appello dell'altro insegnamento
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    void testGetStudentiByAppello_AppelloConStudenti() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appello, studenti, and confirm them
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
            ems.setAppelloCorrente(appello);

            Studente studente1 = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente1);
            // Set studente1 details
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
            // Set studente2 details
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

            // 2. Execution: Get students by appello
            List<Studente> studenti = ems.getStudentiByAppello();

            // 3. Assertions: Verify the students are retrieved correctly
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
            // 1. Setup: Create docente, studente, and confirm them
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

            // 2. Execution: Get studente by matricola
            Studente retrievedStudente = ems.getStudente("12345");

            // 3. Assertions: Verify the student is retrieved correctly
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

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Scritto", insegnamento);

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
            ems.prenotaAppello(appello1);
            ems.prenotaAppello(appello2);

            // Inserisci esiti per lo studente
            ems.setAppelloCorrente(appello1); // Imposta appello corrente
            ems.inserisciEsito("12345", "25", "Approvato");

            ems.setAppelloCorrente(appello2); // Imposta appello corrente
            ems.inserisciEsito("12345", "30", "Approvato");

            // 2. Execution: Calculate average grade
            double media = ems.calcolaMediaVoti(studente);

            // 3. Assertions: Verify the average is calculated correctly
            assertEquals(27.5, media); // (25 + 30) / 2 = 27.5
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testControlloEsistenzaAppello_AppelloEsistente() throws Exception {
        try {
            // 1. Setup: Create docente, insegnamento, appello, and confirm them
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

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";

            Appello_esame appello = new Appello_esame("APP-1", data, orario, luogo, 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello); // Aggiungi l'appello all'insegnamento

            // Imposta insegnamentoSelezionato
            ems.setInsegnamentoSelezionato(insegnamento);

            // 2. Execution: Check if the appello exists
            boolean esiste = ems.controlloEsistenzaAppello(data, orario, luogo);

            // 3. Assertions: Verify that the method returns true
            assertTrue(esiste);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testGetPrenotazioniNonRecensiteByStudente_PrenotazioniNonRecensite() throws Exception {
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

            Appello_esame appello1 = new Appello_esame("APP-1", LocalDate.now(), LocalTime.now(), "Aula A", 30, "Scritto", insegnamento);
            ems.setAppelloCorrente(appello1);

            Appello_esame appello2 = new Appello_esame("APP-2", LocalDate.now(), LocalTime.now(), "Aula B", 30, "Scritto", insegnamento);

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
            ems.prenotaAppello(appello1);
            ems.prenotaAppello(appello2);

            // 2. Execution: Get non-reviewed reservations
            HashMap<String, Prenotazione> prenotazioni = ems.getPrenotazioniNonRecensiteByStudente();

            // 3. Assertions: Verify that the correct reservations are returned
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
            // 1. Setup: Crea studente, insegnamento, appelli, prenotazioni e esiti
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

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

            // Crea prenotazioni e esiti
            Prenotazione prenotazione1 = new Prenotazione("PREN-1", data, ora, 1, studente, appello1);
            Esito_esame esito1 = new Esito_esame("28", "Approvato", studente, appello1);
            prenotazione1.setEsito(esito1);
            ems.getReservation_list().put("PREN-1", prenotazione1);

            Prenotazione prenotazione2 = new Prenotazione("PREN-2", data, ora, 2, studente, appello2);
            Esito_esame esito2 = new Esito_esame("25", "Approvato", studente, appello2);
            prenotazione2.setEsito(esito2);
            ems.getReservation_list().put("PREN-2", prenotazione2);

            // Crea una prenotazione non approvata
            Prenotazione prenotazione3 = new Prenotazione("PREN-3", data, ora, 3, studente, appello1);
            Esito_esame esito3 = new Esito_esame("15", "Non Approvato", studente, appello1);
            prenotazione3.setEsito(esito3);
            ems.getReservation_list().put("PREN-3", prenotazione3);

            // 2. Esecuzione: Recupera la lista degli appelli approvati
            List<String> appelliApprovati = ems.getAppelliApprovati();

            // 3. Asserzioni: Verifica che la lista contenga gli appelli approvati corretti
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
            // 1. Setup: Crea un insegnamento e un appello
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate dataOriginale = LocalDate.now();
            LocalTime orarioOriginale = LocalTime.now();
            String luogoOriginale = "Aula A";

            Appello_esame appello = new Appello_esame("APP-1", dataOriginale, orarioOriginale, luogoOriginale, 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            // Imposta appello corrente
            ems.setAppelloCorrente(appello);

            // 2. Esecuzione: Chiama la funzione con nuovi dati
            LocalDate dataNuova = LocalDate.now().plusDays(1);
            LocalTime orarioNuovo = LocalTime.now().plusHours(1);
            String luogoNuovo = "Aula B";

            ems.reinserisciDatiAppello(dataNuova, orarioNuovo, luogoNuovo);

            // 3. Asserzioni: Verifica che i dati dell'appello siano stati aggiornati
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
            // 1. Setup: Crea studente, insegnamento, appello, prenotazione e imposta i valori correnti
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            Prenotazione prenotazione = new Prenotazione("PREN-1", data, ora, 1, studente, appello);
            ems.getReservation_list().put("PREN-1", prenotazione);

            // Imposta prenotazione corrente
            ems.setPrenotazioneCorrente(prenotazione);

            // 2. Esecuzione: Chiama la funzione aggiungiFeedback
            Optional<String> feedback = Optional.of("Ottimo appello!");
            ems.aggiungiFeedback(feedback);

            // 3. Asserzioni: Verifica che il feedback sia stato aggiunto e la prenotazione recensita
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
            // 1. Setup: Crea insegnamento, appello e imposta appello corrente
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime ora = LocalTime.now();

            Appello_esame appello = new Appello_esame("APP-1", data, ora, "Aula A", 30, "Scritto", insegnamento);
            insegnamento.aggiungiAppello(appello);

            // Aggiungi feedback all'appello
            appello.addFeedback("Ottimo appello!");
            appello.addFeedback("Ben organizzato.");

            // Imposta appello corrente
            ems.setAppelloCorrente(appello);

            // 2. Esecuzione: Chiama la funzione getFeedback
            List<String> feedbacks = ems.getFeedback();

            // 3. Asserzioni: Verifica che la lista dei feedback sia corretta
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
            // 1. Setup: Crea uno studente e lo conferma
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

            // 2. Esecuzione: Chiama la funzione modificaProfilo con nuovi dati
            String residenzaNuova = "Roma";
            String emailNuova = "mario.rossi.nuovo@example.com";
            String telefonoNuovo = "9876543210";

            ems.modificaProfilo(residenzaNuova, emailNuova, telefonoNuovo);

            // 3. Asserzioni: Verifica che i dati dello studente siano stati aggiornati
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
            // 1. Setup: Crea un docente e lo conferma
            Docente docente = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente);
            // Dati docente (esempio)
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

            // 2. Esecuzione: Chiama la funzione modificaProfilo con nuovi dati
            String residenzaNuova = "Milano";
            String emailNuova = "luigi.verdi.nuovo@example.com";
            String telefonoNuovo = "1234567890";

            ems.modificaProfilo(residenzaNuova, emailNuova, telefonoNuovo);

            // 3. Asserzioni: Verifica che i dati del docente siano stati aggiornati
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
            // 1. Setup: Crea un appello con data a meno di 3 giorni da oggi
            LocalDate dataAppello = LocalDate.now().plusDays(2);
            Appello_esame appello = new Appello_esame("APP-1", dataAppello, LocalTime.now(), "Aula A", 30, "Scritto", null);

            // 2. Esecuzione: Chiama la funzione
            boolean troppoTardi = ems.isTroppoTardiPerCancellare(appello);

            // 3. Asserzioni: Verifica che la funzione restituisca true
            assertTrue(troppoTardi);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testHaRicevutoEsito_EsitoPresente() throws Exception {
        try {
            // 1. Setup: Crea studente, appello, prenotazione ed esito
            Studente studente = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente);
            // Dati studente (esempio)
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

            ems.confermaUtente(); // Conferma lo studente

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

            // 2. Esecuzione: Chiama la funzione
            boolean esitoPresente = ems.haRicevutoEsito(appello);

            // 3. Asserzioni: Verifica che la funzione restituisca true
            assertTrue(esitoPresente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }










    }












