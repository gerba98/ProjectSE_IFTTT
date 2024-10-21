package com.ccll.projectse_ifttt.Triggers;

/**
 * Rappresenta un creatore di trigger per l'attivazione ad un valore specifico ritornato da un programma specifico.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {EPTrigCreator}.
 */
public class EPTrigCreator extends TriggerCreator {
    /**
     * Crea un nuovo trigger di tipo {ExecutionProgramTrig} con l'output desiderato e il programma specificato.
     *
     * @return un nuovo oggetto {Trigger} che si attiva quando il programma specificato restituisce l'output desiderato.
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        return new ExecutionProgramTrig(triggerValue);
    }

    @Override
    public String getType() {
        return "status program";
    }

}
