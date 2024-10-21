package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionProgramTrigTest {
//    @Test
//    @DisplayName("Verifica attivazione del trigger quando l'output del programma corrisponde al valore atteso")
//    void testEvaluationWithMatchingOutput() {
//        // Creiamo un trigger con un programma che restituisce un codice di uscita 0 (successo)
//        ExecutionProgramTrig trigger = new ExecutionProgramTrig("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-1");
//
//        // Verifichiamo che il trigger si attivi correttamente
//        Assertions.assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi quando l'output corrisponde al valore atteso");
//    }
//
//    @Test
//    @DisplayName("Verifica mancata attivazione del trigger quando l'output del programma non corrisponde al valore atteso")
//    void testEvaluationWithNonMatchingOutput() {
//        // Creiamo un trigger con un programma che restituisce un codice di uscita 1 (errore)
//        ExecutionProgramTrig trigger = new ExecutionProgramTrig("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-0");
//
//        // Verifichiamo che il trigger non si attivi
//        Assertions.assertFalse(trigger.evaluate(), "Il trigger non dovrebbe attivarsi quando l'output non corrisponde al valore atteso");
//    }
//
//    @Test
//    @DisplayName("Verifica reset del trigger dopo l'attivazione")
//    void testResetAfterActivation() {
//        // Creiamo un trigger con un programma che restituisce un codice di uscita 0 (successo)
//        ExecutionProgramTrig trigger = new ExecutionProgramTrig("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-1");
//
//        // Verifichiamo che il trigger si attivi correttamente
//        Assertions.assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi quando l'output corrisponde al valore atteso");
//
//        // Resettiamo il trigger
//        trigger.reset();
//
//        // Verifichiamo che il trigger non sia più attivo
//        Assertions.assertFalse(trigger.evaluate(), "Il trigger non dovrebbe più essere attivo dopo il reset");
//    }
//
//    @Test
//    @DisplayName("Verifica aggiornamento del comando e del programma del trigger")
//    void testUpdateCommandAndProgram() {
//        // Creiamo un trigger con un programma che restituisce un codice di uscita 0 (successo)
//        ExecutionProgramTrig trigger = new ExecutionProgramTrig("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-1");
//
//        // Verifichiamo che il trigger si attivi correttamente
//        Assertions.assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi quando l'output corrisponde al valore atteso");
//
//        // Aggiorniamo il comando e il programma del trigger
//        trigger.setUserInfo(" -C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\dist\\file.exe-1");
//
//        // Verifichiamo che il trigger si attivi con il nuovo comando e programma
//        Assertions.assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi con il nuovo comando e programma");
//    }
//
//    @Test
//    @DisplayName("Verifica attivazione del trigger con un programma senza argomenti")
//    void testEvaluationWithProgramWithoutArguments() {
//        // Creiamo un trigger con un programma che non richiede argomenti
//        ExecutionProgramTrig trigger = new ExecutionProgramTrig("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-1");
//
//        // Verifichiamo che il trigger si attivi correttamente
//        Assertions.assertTrue(trigger.evaluate(), "Il trigger dovrebbe attivarsi quando l'output corrisponde al valore atteso");
//    }
    @Test
    @DisplayName("Valutazione del trigger all'output del programma specificato")
    public void testEvaluation() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-1";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        assertTrue(executionProgramTrig.evaluate(), "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");

        executionProgramTrig.setUserInfo("py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-0");
        assertFalse(executionProgramTrig.evaluate(), "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");
    }

    @Test
    @DisplayName("Inizializzazione di ExecutionProgramTrig con comando, programma e output specificati")
    public void testInitialization() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-Azione eseguita";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        assertEquals(userInfo, executionProgramTrig.getUserInfo());
    }

    @Test
    @DisplayName("Creazione di DayOfTheMonthTrig tramite DOTMTrigCreator")
    public void testCreator() {
        String userInfo = "py-C:\\Users\\cuozz\\OneDrive\\Desktop\\exe\\file.py-Azione eseguita";
        TriggerCreator EP = new EPTrigCreator();
        Trigger trigger = EP.createTrigger(userInfo);
        assertInstanceOf(ExecutionProgramTrig.class, trigger);
    }
}