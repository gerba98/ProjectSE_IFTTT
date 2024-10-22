package com.ccll.projectse_ifttt.Triggers;


import java.util.Arrays;

public class FileDimTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileDimensionTrig} con la dimensione, l'unità di misura e il file da controllare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al raggiungimento della dimensione specificata del file specificato.
     * value=dimension, unitDim parts[1], filePath parts[2]+":"+parts[3] concatenazione necessaria per rendere valido il path
     */
    @Override
    public Trigger createTrigger(String dimension) {
        System.out.println(dimension);
        String[] parts = dimension.split(":");
        int value = Integer.parseInt(parts[0]);
        String path = String.join(":", Arrays.copyOfRange(parts, 2, parts.length));
        return new FileDimensionTrig(value, parts[1], path);

    }
        @Override
        public String getType () {
            return "file dimension";
        }
    }
