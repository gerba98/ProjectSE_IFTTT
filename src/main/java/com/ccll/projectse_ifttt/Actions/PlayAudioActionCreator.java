package com.ccll.projectse_ifttt.Actions;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * La classe {@code PlayAudioActionCreator} estende {@link ActionCreator} e si occupa della creazione
 * di istanze di {@link PlayAudioAction} utilizzando un percorso specificato per il file audio.
 */
public class PlayAudioActionCreator extends ActionCreator {

    /**
     * Crea e restituisce una nuova istanza di {@link PlayAudioAction} utilizzando il percorso del file audio
     * fornito tramite la stringa {@code actionValue}.
     *
     * @param actionValue il percorso del file audio in formato stringa
     * @return un'istanza di {@link PlayAudioAction} configurata con il percorso del file audio
     */
    @Override
    public Action createAction(String actionValue) {
        Path audioFilePath = Paths.get(actionValue);
        return new PlayAudioAction(audioFilePath);
    }

    /**
     * Restituisce il tipo di azione supportata da questo creatore.
     *
     * @return una stringa che rappresenta il tipo di azione, in questo caso "play audio"
     */
    @Override
    public String getType() {
        return "play audio";
    }
}
