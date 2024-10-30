package com.ccll.projectse_ifttt.Triggers;


import java.util.Arrays;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati sulla dimensione di un file.
 * Fornisce un metodo per creare istanze di {@link FileDimensionTrig}, che si attivano quando il file specificato
 * raggiunge una dimensione predefinita.
 */
public class FileDimTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link FileDimensionTrig} che si attiva al raggiungimento della dimensione specificata del file.
     * La stringa di input deve essere fornita nel formato "dimensione:unità:percorso", dove:
     * <ul>
     *     <li>{@code dimensione} è la dimensione limite per l'attivazione (in interi).</li>
     *     <li>{@code unità} è l'unità di misura della dimensione (es. B, KB, MB).</li>
     *     <li>{@code percorso} è il percorso completo del file da monitorare.</li>
     * </ul>
     *
     * @param dimension una stringa formattata che specifica la dimensione, l'unità di misura e il percorso del file.
     * @return un nuovo oggetto {@link Trigger} che si attiva al raggiungimento della dimensione specificata del file.
     */
    @Override
    public Trigger createTrigger(String dimension) {
        String[] parts = dimension.split("-");
        int value = Integer.parseInt(parts[1]);
        String unitDim = parts[2];
        String path = parts[0];
        return new FileDimensionTrig(value, unitDim, path);

    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "file dimension".
     */
    @Override
    public String getType() {
        return "file dimension";
    }
}
