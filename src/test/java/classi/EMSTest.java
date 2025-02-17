package classi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
    void testCreaProfiloUtente_UtenteNonCorrente() {
        try {
            // 1. Setup: Non imposta l'utente corrente
            String nome = "Mario";
            String cognome = "Rossi";
            Date dataNascita = new Date(); // Usa un oggetto Date di prova
            String genere = "Maschio";
            String codiceFiscale = "RSSMRO80A01L219X";
            String residenza = "Milano";
            String email = "mario.rossi@example.com";
            String telefono = "1234567890";

            // 2. Esecuzione: Chiama la funzione da testare e verifica che venga lanciata un'eccezione
            assertThrows(NullPointerException.class, () -> {
                ems.creaProfiloUtente(nome, cognome, dataNascita, genere, codiceFiscale, residenza, email, telefono);
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
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
    void testGeneraCredenziali_UtenteNonCorrente() {
        try {
            // 1. Setup: Non imposta l'utente corrente

            // 2. Esecuzione: Chiama la funzione da testare e verifica che venga lanciata un'eccezione
            assertThrows(NullPointerException.class, () -> {
                ems.generaCredenziali();
            });
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
    void testConfermaUtente_UtenteNonCorrente() {
        try {
            // 1. Setup: Non imposta l'utente corrente

            // 2. Esecuzione: Chiama la funzione da testare e verifica che venga lanciata un'eccezione
            assertThrows(NullPointerException.class, () -> {
                ems.confermaUtente();
            });
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
                ems.loginStudente("12345", "password123");
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
        // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
        try {
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            // Dati appello
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare
            String idAppello = ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);

            // 3. Asserzioni: Verifica che l'appello sia stato creato e aggiunto alle liste
            assertNotNull(idAppello);
            assertNull(ems.getExam_list().get(idAppello)); // L'appello NON deve essere in exam_list dopo creazioneAppello

            // 4. Imposta appello corrente (usando l'ID)
            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            ems.setAppelloCorrente(appello);

            // 5. Esecuzione: Conferma l'appello
            ems.confermaAppello();

            // 6. Asserzioni: Verifica che l'appello sia stato aggiunto a exam_list dopo confermaAppello
            assertNotNull(ems.getExam_list().get(idAppello)); // L'appello deve essere in exam_list dopo confermaAppello
            assertEquals(insegnamento, ems.getExam_list().get(idAppello).getInsegnamento());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }



    @Test
    void testCreazioneAppello_InsegnamentoNonTrovato() {
        try {
            // 1. Setup: Non aggiungo l'insegnamento alla lista

            // Dati appello
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare e verifica che venga lanciata l'eccezione
            assertThrows(IllegalArgumentException.class, () -> {
                ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);
            });
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testCreazioneAppello_AppelloEsistente() {
        try {
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023); // Usa il costruttore corretto
            ems.getTeaching_list().put("INF-01", insegnamento); // Usa il getter

            // Crea un appello esistente
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            ems.getExam_list().put("APP-1", appelloEsistente); // Usa il getter

            // 2. Esecuzione: Chiama la funzione da testare con gli stessi dati e verifica che venga restituito null
            String idAppello = ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);

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

            // Crea un appello esistente
            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";
            Appello_esame appelloEsistente = new Appello_esame("APP-1", data, orario, luogo, postiDisponibili, tipologia, insegnamento);
            ems.getExam_list().put("APP-1", appelloEsistente);

            // 2. Esecuzione: Chiama la funzione da testare con gli stessi dati
            String idAppello = ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);

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

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            // 2. Esecuzione: Chiama la funzione da testare
            String idAppello = ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);

            // 3. Crea l'oggetto Appello_esame *direttamente* usando i dati
            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);

            // 4. Imposta appello corrente *con l'oggetto appena creato*
            ems.setAppelloCorrente(appello);

            // 5. Esecuzione: Conferma l'appello
            ems.confermaAppello();

            // 6. Asserzioni: Verifica che l'appello sia presente nella mappa exam_list di EMS
            assertNotNull(ems.getExam_list().get(idAppello));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testConfermaAppello_AppelloGiaConfermato() {
        try {
            // 1. Setup: Crea un insegnamento e lo aggiungi alla lista
            Insegnamento insegnamento = new Insegnamento("INF-01", "Informatica", 6, "Descrizione", 2023);
            ems.getTeaching_list().put("INF-01", insegnamento);

            LocalDate data = LocalDate.now();
            LocalTime orario = LocalTime.now();
            String luogo = "Aula A";
            int postiDisponibili = 30;
            String tipologia = "Scritto";

            String idAppello = ems.creazioneAppello("INF-01", data, orario, luogo, postiDisponibili, tipologia);

            // Crea l'oggetto Appello_esame *direttamente* usando i dati
            Appello_esame appello = new Appello_esame(idAppello, data, orario, luogo, postiDisponibili, tipologia, insegnamento);

            // Imposta appello corrente *con l'oggetto appena creato*
            ems.setAppelloCorrente(appello);

            // Conferma l'appello la prima volta
            ems.confermaAppello();

            // ***RECUPERA L'APPELLO DALLA MAPPA DI EMS DOPO LA PRIMA CONFERMA***
            appello = ems.getExam_list().get(idAppello); // <--- Questa è la riga cruciale!
            ems.setAppelloCorrente(appello); // <--- E reimposta appelloCorrente con l'oggetto *corretto*

            // 2. Esecuzione: Tenta di confermare l'appello una seconda volta (non dovrebbe fare nulla)
            ems.confermaAppello();

            // 3. Asserzioni: Verifica che l'appello sia presente nella mappa exam_list di EMS (e che non sia stato aggiunto di nuovo)
            assertNotNull(ems.getExam_list().get(idAppello));
            assertEquals(1, ems.getExam_list().size()); // Verifica che ci sia un solo appello nella mappa
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void getInsegnamenti() {
    }

    @Test
    void getAppelliByInsegnamento() {
    }

    @Test
    void prenotaAppello() {
    }


    @Test
    void visualizzaAppelliPerInsegnamento() {
    }
}