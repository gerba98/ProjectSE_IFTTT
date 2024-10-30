package com.ccll.projectse_ifttt.Triggers;


import java.util.Arrays;

public class FileDimTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileDimensionTrig} con la dimensione, l'unità di misura e il file da controllare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al raggiungimento della dimensione specificata del file specificato.
     * value=parts[1], unitDim parts[2], filePath parts[0]
     */
    @Override
    public Trigger createTrigger(String dimension) {
        String[] parts = dimension.split("-");
        int value = Integer.parseInt(parts[1]);
        String unitDim = parts[2];
        String path = parts[0];
        return new FileDimensionTrig(value, unitDim, path);

    }
        @Override
        public String getType () {
            return "file dimension";
        }
    }
