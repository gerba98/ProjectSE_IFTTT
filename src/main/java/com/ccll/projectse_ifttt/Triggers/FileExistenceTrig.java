package com.ccll.projectse_ifttt.Triggers;

import java.io.File;
import java.nio.file.Path;

/**
 * Implementa un trigger che si attiva se è presente un determinato file nel percorso specificato
 */
public class FileExistenceTrig extends AbstractTrigger {
    private File file;

    /**
     * Costruisce un FileExistenceTrig con il percorso del file per il quale deve essere verificata l'esistenza.
     *
     * @param file è il percorso del file da controllare
     */
    public FileExistenceTrig(File file) {
        this.file = file;
    }

    /**
     * Restituisce il percorso del file.
     *
     * @return il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public File getFile() {
        return file;
    }

    /**
     * Imposta il percorso del file per il quale deve essere verificata l'esistenza.
     *
     * @param file il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è soddisfatta se il file esiste.
     *
     * @return true se la condizione è soddisfatta, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return file.exists();
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa in cui è specificato il trigger type e il trigger value separato da ";"
     */
    @Override
    public String toString() {
        return "File existence;" + file.getParent()+"-"+file.getName();
    }
}
