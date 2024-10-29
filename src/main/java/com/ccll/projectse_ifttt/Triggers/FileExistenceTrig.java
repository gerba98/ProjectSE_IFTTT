package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

/**
 * Implementa un trigger che si attiva in base alla presenza di un file in un percorso specificato.
 * Questo trigger verifica se un determinato file esiste nel percorso fornito.
 */
public class FileExistenceTrig extends AbstractTrigger {
    private String filePath;

    /**
     * Costruisce un trigger basato sull'esistenza di un file specifico.
     *
     * @param filePath il percorso del file da monitorare per verificarne l'esistenza.
     */
    public FileExistenceTrig(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Restituisce il percorso del file monitorato da questo trigger.
     *
     * @return il percorso del file per il quale viene verificata l'esistenza.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Imposta un nuovo percorso del file da monitorare.
     *
     * @param filePath il percorso del file per il quale deve essere verificata l'esistenza.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * La condizione è considerata soddisfatta se il file esiste nel percorso specificato.
     *
     * @return true se il file esiste, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return new File(filePath).exists();
    }

    /**
     * Fornisce una rappresentazione stringa di questo trigger.
     *
     * @return una stringa che descrive il tipo di trigger e il percorso del file monitorato, separati da un punto e virgola.
     */
    @Override
    public String toString() {
        return "File existence; " + filePath;
    }
}
