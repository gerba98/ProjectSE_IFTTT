package com.ccll.projectse_ifttt.Triggers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionProgramTrigTest {

    @Test
    @DisplayName("Valutazione del trigger all'output del programma specificato")
    public void testEvaluation() {
        String resourcePath = getClass().getResource("/com/ccll/projectse_ifttt/programs/file.py").getPath();
        // Se usi Windows, rimuovi il carattere iniziale '/' se presente
        if (resourcePath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("windows")) {
            resourcePath = resourcePath.substring(1);
        }

        String userInfo = "py-" + resourcePath + "-1";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        // Prima chiamata per avviare il processo
        executionProgramTrig.evaluate();

        // Polling con timeout
        long startTime = System.currentTimeMillis();
        long timeout = 5000; // 5 secondi di timeout
        boolean result = false;

        while (System.currentTimeMillis() - startTime < timeout) {
            if (executionProgramTrig.evaluate()) {
                result = true;
                break;
            }
            try {
                Thread.sleep(100); // Breve pausa tra i tentativi
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        assertTrue(result, "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");

        userInfo = "py-" + resourcePath + "-0";

        executionProgramTrig.setUserInfo(userInfo);
        assertFalse(executionProgramTrig.evaluate(), "Il trigger dovrebbe attivarsi quando l'output del programma specificato è uguale a quello desiderato");
    }

    @Test
    @DisplayName("Inizializzazione di ExecutionProgramTrig con comando, programma e output specificati")
    public void testInitialization() {
        String resourcePath = getClass().getResource("/com/ccll/projectse_ifttt/programs/file.py").getPath();
        // Se usi Windows, rimuovi il carattere iniziale '/' se presente
        if (resourcePath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("windows")) {
            resourcePath = resourcePath.substring(1);
        }

        String userInfo = "py-" + resourcePath + "-1";
        ExecutionProgramTrig executionProgramTrig = new ExecutionProgramTrig(userInfo);

        assertEquals(userInfo, executionProgramTrig.getUserInfo());
    }

    @Test
    @DisplayName("Creazione di DayOfTheMonthTrig tramite DOTMTrigCreator")
    public void testCreator() {
        String resourcePath = getClass().getResource("/com/ccll/projectse_ifttt/programs/file.py").getPath();
        // Se usi Windows, rimuovi il carattere iniziale '/' se presente
        if (resourcePath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("windows")) {
            resourcePath = resourcePath.substring(1);
        }

        String userInfo = "py-" + resourcePath + "-1";
        TriggerCreator EP = new EPTrigCreator();
        Trigger trigger = EP.createTrigger(userInfo);
        assertInstanceOf(ExecutionProgramTrig.class, trigger);
    }
}