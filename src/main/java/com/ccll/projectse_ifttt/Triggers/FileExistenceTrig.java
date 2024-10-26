package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

/**
 * Implementa un trigger che si attiva se è presente un determinato file nel percorso specificato
 */
public class FileExistenceTrig extends AbstractTrigger {
    private String filePath;

    /**
     * Costruisce un FileExistenceTrig con il percorso del file per il quale deve essere verificata l'esistenza.
     *
     * @param filePath è il percorso del file da controllare
     */
    public FileExistenceTrig(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Restituisce il percorso del file.
     *
     * @return il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Imposta il percorso del file per il quale deve essere verificata l'esistenza.
     *
     * @param filePath il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è soddisfatta se il file esiste.
     *
     * @return true se la condizione è soddisfatta, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return new File(filePath).exists();
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa in cui è specificato il trigger type e il trigger value separato da ";"
     */
    @Override
    public String toString() {
        return "File existence;" + filePath;
    }
}
