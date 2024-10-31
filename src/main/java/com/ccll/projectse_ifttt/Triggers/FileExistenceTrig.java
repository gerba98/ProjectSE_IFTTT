package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

/**
 * Implementa un trigger che si attiva in base alla presenza di un file in un percorso specificato.
 * Questo trigger verifica se un determinato file esiste nel percorso fornito.
 */
public class FileExistenceTrig extends AbstractTrigger {
    private File file;

    /**
     * Costruisce un trigger basato sull'esistenza di un file specifico.
     *
     * @param file è il percorso del file da controllare
     */
    public FileExistenceTrig(File file) {
        this.file = file;
    }

    /**
     * Restituisce il percorso del file monitorato da questo trigger.
     *
     * @return il percorso del file per il quale viene verificata l'esistenza.
     */
    public File getFile() {
        return file;
    }

    /**
     * Imposta un nuovo percorso del file da monitorare.
     *
     * @param file il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è considerata soddisfatta se il file esiste nel percorso specificato.
     *
     * @return true se il file esiste, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return file.exists();
    }

    /**
     * Fornisce una rappresentazione stringa di questo trigger.
     *
     * @return una stringa che descrive il tipo di trigger e il percorso del file monitorato, separati da un punto e virgola.
     */
    @Override
    public String toString() {
        return "File existence;" + file.getParent()+"-"+file.getName();
    }
}
