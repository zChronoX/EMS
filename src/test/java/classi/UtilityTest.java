package classi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    static EMS ems;
    static UtenteFactory utenteFactory;

    @BeforeAll
    public static void initTest() {
        ems = EMS.getInstance();
        utenteFactory = new UtenteFactory();

    }

    @Test
    void testLoadStudents() {
        try {

            Utility utility = new Utility();
            HashMap<String, Studente> studentList = utility.loadStudents();


            assertEquals(2, studentList.size());


            for (Map.Entry<String, Studente> entry : studentList.entrySet()) {
                Studente s1 = entry.getValue();
                if (s1.getCodice_fiscale().equals("CNTGNN01D07C351H")) {
                    assertEquals("Giovanni", s1.getNome());
                    assertEquals("Contarino", s1.getCognome());
                    assertEquals("M", s1.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate1 = sdf.format(new Date());
                    String actualDate1 = sdf.format(s1.getData_nascita());

                    assertEquals(expectedDate1, actualDate1);

                    assertEquals("CNTGNN01D07C351H", s1.getCodice_fiscale());
                    assertEquals("Via Blanco 14, Acireale", s1.getResidenza());
                    assertEquals("giovanni.contarino.gc@gmail.com", s1.getEmail());
                    assertEquals("3801577024", s1.getTelefono());
                    assertEquals(Utente.TipoProfilo.Studente, s1.getTipoProfilo());
                    break;
                }
            }


            for (Map.Entry<String, Studente> entry : studentList.entrySet()) {
                Studente s2 = entry.getValue();
                if (s2.getCodice_fiscale().equals("NPPGGPS214312PP")) {
                    assertEquals("Giuseppe", s2.getNome());
                    assertEquals("Nappo", s2.getCognome());
                    assertEquals("M", s2.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate2 = sdf.format(new Date());
                    String actualDate2 = sdf.format(s2.getData_nascita());

                    assertEquals(expectedDate2, actualDate2);

                    assertEquals("NPPGGPS214312PP", s2.getCodice_fiscale());
                    assertEquals("Via Scaloto 12, Scala", s2.getResidenza());
                    assertEquals("nappopippo@gmail.com", s2.getEmail());
                    assertEquals("1234567890", s2.getTelefono());
                    assertEquals(Utente.TipoProfilo.Studente, s2.getTipoProfilo());
                    break;
                }
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoadProfessors_CaricamentoCorretto() {
        try {

            Utility utility = new Utility();
            HashMap<String, Docente> professorList = utility.loadProfessors();


            assertEquals(4, professorList.size());


            for (Map.Entry<String, Docente> entry : professorList.entrySet()) {
                Docente d1 = entry.getValue();
                if (d1.getCodice_fiscale().equals("RSSMRO70E10F900X")) {
                    assertEquals("Mario", d1.getNome());
                    assertEquals("Rossi", d1.getCognome());
                    assertEquals("M", d1.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate1 = sdf.format(new Date());
                    String actualDate1 = sdf.format(d1.getData_nascita());

                    assertEquals(expectedDate1, actualDate1);

                    assertEquals("RSSMRO70E10F900X", d1.getCodice_fiscale());
                    assertEquals("Via Roma 1, Milano", d1.getResidenza());
                    assertEquals("mario.rossi@example.com", d1.getEmail());
                    assertEquals("3331234567", d1.getTelefono());
                    assertEquals(Utente.TipoProfilo.Docente, d1.getTipoProfilo());
                    break;
                }
            }


            for (Map.Entry<String, Docente> entry : professorList.entrySet()) {
                Docente d2 = entry.getValue();
                if (d2.getCodice_fiscale().equals("VRDLUG65K25G800Y")) {
                    assertEquals("Luigi", d2.getNome());
                    assertEquals("Verdi", d2.getCognome());
                    assertEquals("M", d2.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate2 = sdf.format(new Date());
                    String actualDate2 = sdf.format(d2.getData_nascita());

                    assertEquals(expectedDate2, actualDate2);

                    assertEquals("VRDLUG65K25G800Y", d2.getCodice_fiscale());
                    assertEquals("Via Verdi 2, Roma", d2.getResidenza());
                    assertEquals("luigi.verdi@example.com", d2.getEmail());
                    assertEquals("3479876543", d2.getTelefono());
                    assertEquals(Utente.TipoProfilo.Docente, d2.getTipoProfilo());
                    break;
                }
            }


            for (Map.Entry<String, Docente> entry : professorList.entrySet()) {
                Docente d3 = entry.getValue();
                if (d3.getCodice_fiscale().equals("BNCANN82C08H900Z")) {
                    assertEquals("Anna", d3.getNome());
                    assertEquals("Bianchi", d3.getCognome());
                    assertEquals("F", d3.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate3 = sdf.format(new Date());
                    String actualDate3 = sdf.format(d3.getData_nascita());

                    assertEquals(expectedDate3, actualDate3);

                    assertEquals("BNCANN82C08H900Z", d3.getCodice_fiscale());
                    assertEquals("Via Bianchi 3, Torino", d3.getResidenza());
                    assertEquals("anna.bianchi@example.com", d3.getEmail());
                    assertEquals("3281011122", d3.getTelefono());
                    assertEquals(Utente.TipoProfilo.Docente, d3.getTipoProfilo());
                    break;
                }
            }


            for (Map.Entry<String, Docente> entry : professorList.entrySet()) {
                Docente d4 = entry.getValue();
                if (d4.getCodice_fiscale().equals("NRIGVN78I15I000W")) {
                    assertEquals("Giovanni", d4.getNome());
                    assertEquals("Neri", d4.getCognome());
                    assertEquals("M", d4.getGenere());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String expectedDate4 = sdf.format(new Date());
                    String actualDate4 = sdf.format(d4.getData_nascita());

                    assertEquals(expectedDate4, actualDate4);

                    assertEquals("NRIGVN78I15I000W", d4.getCodice_fiscale());
                    assertEquals("Via Neri 4, Napoli", d4.getResidenza());
                    assertEquals("giovanni.neri@example.com", d4.getEmail());
                    assertEquals("3394445555", d4.getTelefono());
                    assertEquals(Utente.TipoProfilo.Docente, d4.getTipoProfilo());
                    break;
                }
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testLoadCourses_CaricamentoCorretto() throws IOException {
        try {

            Utility utility = new Utility();
            HashMap<String, Docente> docenti = new HashMap<>();
            HashMap<String, Studente> studenti = new HashMap<>();


            Docente docente1 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente1);

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
            docenti.put(docente1.getCodiceDocente(), docente1);

            Docente docente2 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente2);

            docente2.setNome("Mario");
            docente2.setCognome("Rossi");
            docente2.setData_nascita(new Date());
            docente2.setGenere("Maschio");
            docente2.setCodice_fiscale("RSSMRO80A01L735A");
            docente2.setResidenza("Milano");
            docente2.setEmail("mario.rossi@example.com");
            docente2.setTelefono("1234567890");
            docente2.setCodiceDocente("MR987");
            docente2.setPassword("password123");
            ems.confermaUtente();
            docenti.put(docente2.getCodiceDocente(), docente2);

            Docente docente3 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente3);

            docente3.setNome("Anna");
            docente3.setCognome("Bianchi");
            docente3.setData_nascita(new Date());
            docente3.setGenere("Femmina");
            docente3.setCodice_fiscale("BNCANN82C08H900Z");
            docente3.setResidenza("Torino");
            docente3.setEmail("anna.bianchi@example.com");
            docente3.setTelefono("3281011122");
            docente3.setCodiceDocente("AB987");
            docente3.setPassword("password123");
            ems.confermaUtente();
            docenti.put(docente3.getCodiceDocente(), docente3);

            Docente docente4 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente4);

            docente4.setNome("Giovanni");
            docente4.setCognome("Neri");
            docente4.setData_nascita(new Date());
            docente4.setGenere("Maschio");
            docente4.setCodice_fiscale("NRIGVN78I15I000W");
            docente4.setResidenza("Napoli");
            docente4.setEmail("giovanni.neri@example.com");
            docente4.setTelefono("3394445555");
            docente4.setCodiceDocente("GN987");
            docente4.setPassword("password123");
            ems.confermaUtente();
            docenti.put(docente4.getCodiceDocente(), docente4);


            Studente studente1 = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente1);

            studente1.setNome("Giovanni");
            studente1.setCognome("Contarino");
            studente1.setData_nascita(new Date());
            studente1.setGenere("Maschio");
            studente1.setCodice_fiscale("CNTGNN01D07C351H");
            studente1.setResidenza("Acireale");
            studente1.setEmail("giovanni.contarino.gc@gmail.com");
            studente1.setTelefono("3801577024");
            studente1.setMatricola("S001");
            studente1.setPassword("password");
            studente1.setCategoria("In corso");
            studente1.setAnnoCorso(2025);
            ems.confermaUtente();
            studenti.put(studente1.getMatricola(), studente1);

            Studente studente2 = (Studente) utenteFactory.newUser(Utente.TipoProfilo.Studente);
            ems.setUtenteCorrente(studente2);

            studente2.setNome("Giuseppe");
            studente2.setCognome("Nappo");
            studente2.setData_nascita(new Date());
            studente2.setGenere("Maschio");
            studente2.setCodice_fiscale("NPPGGPS214312PP");
            studente2.setResidenza("Scala");
            studente2.setEmail("nappopippo@gmail.com");
            studente2.setTelefono("1234567890");
            studente2.setMatricola("S002");
            studente2.setPassword("password");
            studente2.setCategoria("In corso");
            studente2.setAnnoCorso(2025);
            ems.confermaUtente();
            studenti.put(studente2.getMatricola(), studente2);


            Path filePath = Paths.get("src/main/files/insegnamenti.txt");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            HashMap<String, Insegnamento> insegnamenti = utility.loadCourses(filePath.toString(), docenti, studenti);


            assertNotNull(insegnamenti);
            assertEquals(4, insegnamenti.size());


            Insegnamento corso1 = insegnamenti.get("MAT001");
            assertNotNull(corso1);
            assertEquals("Algebra Lineare", corso1.getNome());
            assertEquals(6, corso1.getCFU());
            assertEquals("Corso di algebra lineare", corso1.getDescrizione());
            assertEquals(2023, corso1.getAnno());
            assertEquals(2, corso1.getDocenti().size());
            assertTrue(corso1.getDocenti().contains(docente1));
            assertTrue(corso1.getDocenti().contains(docente2));
            assertEquals(2, corso1.getStudenti().size());

        }
        catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testTrovaDocente_DocentePresente() throws IOException {
        try {

            Utility utility = new Utility();
            HashMap<String, Docente> docenti = new HashMap<>();
            HashMap<String, Studente> studenti = new HashMap<>();


            Docente docente1 = (Docente) utenteFactory.newUser(Utente.TipoProfilo.Docente);
            ems.setUtenteCorrente(docente1);

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
            docenti.put(docente1.getCodiceDocente(), docente1);




            Path filePath = Paths.get("src/main/files/insegnamenti.txt");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            HashMap<String, Insegnamento> insegnamenti = utility.loadCourses(filePath.toString(), docenti, studenti);


            assertNotNull(insegnamenti);
            Insegnamento corso = insegnamenti.get("MAT001");
            assertNotNull(corso);
            assertTrue(corso.getDocenti().stream().anyMatch(d -> d.getNome().equals("Luigi") && d.getCognome().equals("Verdi")));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}