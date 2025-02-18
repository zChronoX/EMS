package classi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtenteFactoryTest {
    static EMS ems;
    private UtenteFactory utenteFactory = new UtenteFactory();
    @BeforeAll
    public static void initTest() {
        ems = EMS.getInstance();

    }

    @Test
    void testNewUser_Studente() {
        try {
            Utente utente = utenteFactory.newUser(Utente.TipoProfilo.Studente);
            assertNotNull(utente);
            assertTrue(utente instanceof Studente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testNewUser_Docente() {
        try {
            Utente utente = utenteFactory.newUser(Utente.TipoProfilo.Docente);
            assertNotNull(utente);
            assertTrue(utente instanceof Docente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testNewUser_TipoNonValido() {
        try {
            Utente utente = utenteFactory.newUser(null); // Tipo non valido
            assertNull(utente);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}