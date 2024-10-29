package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

/**
 * Implementa un trigger che si attiva quando un file raggiunge o supera una dimensione specificata.
 * La dimensione di attivazione è definita in una specifica unità di misura (B, KB, MB, GB).
 */
public class FileDimensionTrig extends AbstractTrigger {
    private long dimension;
    private String filePath;
    private String unitDim;

    /**
     * Costruisce un trigger per monitorare la dimensione di un file.
     *
     * @param dimension la dimensione alla quale il trigger si attiva se il file raggiunge o supera questo valore.
     * @param unitDim l'unità di misura della dimensione (B, KB, MB, GB).
     * @param filePath il percorso del file da monitorare.
     */
    public FileDimensionTrig(int dimension, String unitDim, String filePath) {
        this.dimension = dimension;
        this.unitDim = unitDim;
        this.filePath = filePath;
    }

    /**
     * Restituisce la dimensione specificata per l'attivazione di questo trigger.
     *
     * @return la dimensione di attivazione.
     */
    public long getDimension() {
        return dimension;
    }

    /**
     * Imposta una nuova dimensione di attivazione per questo trigger.
     *
     * @param dimension la nuova dimensione alla quale il trigger si attiva.
     */
    public void setDimension(long dimension) {
        this.dimension = dimension;
    }

    /**
     * Restituisce il percorso del file monitorato da questo trigger.
     *
     * @return il percorso del file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Imposta il percorso del file da monitorare per questo trigger.
     *
     * @param filePath il percorso del file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Restituisce l'unità di misura della dimensione utilizzata per il controllo del file.
     *
     * @return l'unità di misura (B, KB, MB, GB).
     */
    public String getUnitDim() {
        return unitDim;
    }

    /**
     * Imposta l'unità di misura della dimensione del file da monitorare.
     *
     * @param unitDim l'unità di misura (B, KB, MB, GB).
     */
    public void setUnitDim(String unitDim) {
        this.unitDim = unitDim;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se la dimensione del file in byte è uguale o superiore alla dimensione specificata.
     *
     * @return true se la dimensione del file è uguale o superiore al valore specificato, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        long dim = switch (unitDim) {
            case "B" -> dimension;
            case "KB" -> dimension * 1024;
            case "MB" -> dimension * 1048576;
            case "GB" -> dimension * 1073741824;
            default -> 0;
        };

        File file = new File(filePath);
        return file.exists() && file.isFile() && file.length() >= dim;
    }

    /**
     * Fornisce una rappresentazione stringa di questo trigger.
     *
     * @return una stringa che rappresenta il tipo di trigger, il percorso del file e la dimensione specificata.
     */
    @Override
    public String toString() {
        return "File dimension; " + filePath + " - " + dimension + " " + unitDim;
    }
}
