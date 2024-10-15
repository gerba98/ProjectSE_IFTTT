package com.ccll.projectse_ifttt.Actions;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * La classe PlayAudioActionCreator estende ActionCreator e si occupa della creazione
 * di istanze di PlayAudioAction utilizzando un percorso specificato per il file audio.
 */
public class PlayAudioActionCreator extends ActionCreator {

    /**
     * Crea e restituisce una nuova istanza di PlayAudioAction con il percorso del file audio configurato.
     *
     * @return un'istanza di PlayAudioAction
     */
    @Override
    public Action createAction(String actionValue) {
        Path audioFilePath = Paths.get(actionValue);
        return new PlayAudioAction(audioFilePath);
    }

    @Override
    public String getType() {
        return "play audio";
    }
}
