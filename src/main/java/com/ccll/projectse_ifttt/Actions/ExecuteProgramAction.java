package com.ccll.projectse_ifttt.Actions;

import java.io.IOException;

/**
 * Rappresenta un'azione per eseguire un'applicazione esterna con argomenti specificati.
 * Questa classe supporta l'esecuzione di applicazioni su macOS e Windows e permette
 * di specificare comandi arbitrari. Una volta eseguita l'azione, non verrà ripetuta.
 */
public class ExecuteProgramAction implements Action {
    private final String programPath; // Il percorso dell'applicazione da eseguire
    private final String command; // I comandi da passare all'applicazione
    private boolean hasExecuted; // Flag per controllare se l'azione è già stata eseguita

    /**
     * Costruttore per inizializzare l'azione di esecuzione dell'applicazione.
     *
     * @param programPath il percorso dell'applicazione da eseguire
     * @param command i comandi da passare all'applicazione (può essere vuoto)
     */
    public ExecuteProgramAction(String programPath, String command) {
        this.programPath = programPath;
        this.command = command;
        this.hasExecuted = false; // Inizialmente, l'azione non è stata eseguita
    }

    /**
     * Esegue l'azione di avvio dell'applicazione con i comandi specificati.
     * Se l'azione è già stata eseguita, non verrà ripetuta.
     *
     * @return true se l'azione è stata eseguita con successo, false se è già stata eseguita o si è verificato un errore
     */
    @Override
    public boolean execute() {
        if (hasExecuted) {
            return false; // Se l'azione è già stata eseguita, non fare nulla
        }

        try {
            ProcessBuilder processBuilder;
            String os = System.getProperty("os.name").toLowerCase();

            // Se è macOS, usa 'open -a' per le applicazioni .app
            if (os.contains("mac")) {
                if (programPath.endsWith(".app")) {
                    processBuilder = new ProcessBuilder("open", "-a", programPath);
                } else {
                    String[] commandArray = command.isEmpty() ? new String[]{programPath} : new String[]{programPath, command};
                    processBuilder = new ProcessBuilder(commandArray);
                }
            } else if (os.contains("win")) {
                // Se è Windows
                String[] commandArray = command.isEmpty() ? new String[]{programPath} : new String[]{programPath, command};
                processBuilder = new ProcessBuilder(commandArray);
            } else {
                throw new UnsupportedOperationException("Operating system not supported: " + os);
            }

            processBuilder.inheritIO(); // Mostra l'output del programma nella console
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            hasExecuted = true; // Imposta il flag a true dopo l'esecuzione
            return exitCode == 0; // Ritorna true se il programma è stato eseguito con successo
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Ritorna false in caso di errore
        }
    }
}
