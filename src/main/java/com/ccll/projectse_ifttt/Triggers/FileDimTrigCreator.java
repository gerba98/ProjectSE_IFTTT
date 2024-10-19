package com.ccll.projectse_ifttt.Triggers;


public class FileDimTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileDimensionTrig} con la dimensione, l'unità di misura e il file da controllare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva al raggiungimento della dimensione specificata del file specificato.
     * value=dimension, unitDim parts[1], filePath parts[2]+":"+parts[3] concatenazione necessaria per rendere valido il path
     */
    @Override
    public Trigger createTrigger(String dimension) {
        String[] parts = dimension.split(":");
        if (parts.length>3) {
            int value = Integer.parseInt(parts[0]);
            return new FileDimensionTrig(value, parts[1], parts[2] + ":" + parts[3]);
        }else{
            String[] partsLoad = dimension.split("-");
            return new FileDimensionTrig(Integer.parseInt(partsLoad[1]),partsLoad[2], partsLoad[0]);
        }
    }

    @Override
    public String getType() {
        return "file dimension";
    }
}
