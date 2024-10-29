package com.ccll.projectse_ifttt.Triggers;

/**
 * Implementazione concreta di {@link TriggerCreator} per la creazione di trigger basati sull'output di un programma specifico.
 * Fornisce un metodo per creare istanze di {@link ExecutionProgramTrig}, che si attivano quando l'output del programma
 * corrisponde al valore specificato.
 */
public class EPTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {@link ExecutionProgramTrig} che si attiva quando il programma specificato restituisce
     * l'output desiderato. La stringa di input deve seguire il formato "command-program-output", dove:
     * <ul>
     *     <li>{@code command} è il comando opzionale da eseguire (può essere vuoto se non necessario).</li>
     *     <li>{@code program} è il programma da eseguire.</li>
     *     <li>{@code output} è il valore dell'output atteso per cui il trigger si attiva.</li>
     * </ul>
     *
     * @param triggerValue una stringa formattata che specifica il comando, il programma e l'output atteso.
     * @return un nuovo oggetto {@link Trigger} che si attiva quando il programma restituisce l'output desiderato.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        return new ExecutionProgramTrig(triggerValue);
    }

    /**
     * Fornisce il tipo di trigger che questa factory è specializzata a creare.
     *
     * @return una stringa che descrive il tipo di trigger, in questo caso "status program".
     */
    @Override
    public String getType() {
        return "status program";
    }
}
