package com.ccll.projectse_ifttt.Actions;

import java.io.IOException;

/**
 * Rappresenta un'azione per eseguire un'applicazione esterna con argomenti specificati.
 * Questa classe supporta l'esecuzione di applicazioni su macOS e Windows e permette
 * di specificare comandi arbitrari.
 */
public class ExecuteProgramAction implements Action {
    private final String programPath; // Il percorso dell'applicazione o del file da eseguire
    private final String command; // I comandi da passare all'applicazione

    /**
     * Costruttore per inizializzare l'azione di esecuzione dell'applicazione.
     *
     * @param programPath il percorso dell'applicazione o del file da eseguire
     * @param command     i comandi da passare all'applicazione (può essere vuoto o nullo)
     */
    public ExecuteProgramAction(String programPath, String command) {
        this.programPath = programPath;
        this.command = command;
    }

    /**
     * Esegue l'azione di avvio dell'applicazione o di esecuzione del comando specificato.
     *
     * @return true se l'azione è stata eseguita con successo, false se si è verificato un errore
     */
    @Override
    public boolean execute() {
        try {
            ProcessBuilder processBuilder;
            String os = System.getProperty("os.name").toLowerCase();

            boolean isApp = programPath.endsWith(".app");

            if (os.contains("mac")) {
                if (isApp) {
                    String[] commandArray = {"open", "-a", programPath};
                    processBuilder = new ProcessBuilder(commandArray);
                } else {
                    String[] commandArray = {"/bin/bash", "-c", command};
                    processBuilder = new ProcessBuilder(commandArray);
                }
            } else if (os.contains("win")) {
                if (command == null || command.trim().isEmpty()) {
                    processBuilder = new ProcessBuilder(programPath);
                } else {
                    processBuilder = new ProcessBuilder(programPath, command);
                }
            } else {
                throw new UnsupportedOperationException("Sistema operativo non supportato: " + os);
            }

            processBuilder.inheritIO(); // Mostra l'output del programma nella console

            // Avvia il processo senza aspettare la sua terminazione
            processBuilder.start();

            return true; // Ritorna subito senza bloccare l'interfaccia grafica
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toString() {
        return "Execution Program;"+programPath+"-"+command;
    }
}
