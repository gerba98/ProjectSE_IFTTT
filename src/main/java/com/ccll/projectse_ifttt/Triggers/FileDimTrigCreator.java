package com.ccll.projectse_ifttt.Triggers;


public class FileDimTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileDimensionTrig} con la dimensione, l'unità di misura e il file da controllare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al raggiungimento della dimensione specificata del file specificato.
     */
    @Override
    public Trigger createTrigger(String dimension) {
        String[] parts = dimension.split(":");
        int value = Integer.parseInt(parts[0]);
        return new FileDimensionTrig(value, parts[1], parts[2]+":"+parts[3]);
    }

    @Override
    public String getType() {
        return "file dimension";
    }
}
