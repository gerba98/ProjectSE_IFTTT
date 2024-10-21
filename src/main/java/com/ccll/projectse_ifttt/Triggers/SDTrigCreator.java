package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;

/**
 * Rappresenta un creatore di trigger per l'attivazione a una data specificata.
 * Questa classe estende la classe {TriggerCreator} e fornisce un metodo per creare
 * un trigger di tipo {SDTrigCreator}.
 */
public class SDTrigCreator  extends TriggerCreator{

    /**
     * Crea un nuovo trigger di tipo {SpecifiedDataTrig} con la data specificata.
     *
     * @return un nuovo oggetto {Trigger} che si attiva alla data specificata.
     */
    @Override
    public Trigger createTrigger(String triggerValue){
        LocalDate date = LocalDate.parse(triggerValue);
        return new SpecifiedDateTrig(date);
    }

    @Override
    public String getType(){return "date";}

}
