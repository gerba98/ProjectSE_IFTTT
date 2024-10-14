package com.ccll.projectse_ifttt.Actions;

import java.nio.file.Path;

/**
 * La classe PlayAudioActionCreator estende ActionCreator e si occupa della creazione
 * di istanze di PlayAudioAction utilizzando un percorso specificato per il file audio.
 */
public class PlayAudioActionCreator extends ActionCreator {

    // Il percorso del file audio per l'azione di riproduzione audio
    private final Path audioFilePath;

    /**
     * Costruttore per inizializzare PlayAudioActionCreator con il percorso del file audio specificato.
     *
     * @param audioFilePath il percorso del file audio da riprodurre
     */
    public PlayAudioActionCreator(Path audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    /**
     * Crea e restituisce una nuova istanza di PlayAudioAction con il percorso del file audio configurato.
     *
     * @return un'istanza di PlayAudioAction
     */
    @Override
    public Action createAction() {
        return new PlayAudioAction(this.audioFilePath);
    }
}
