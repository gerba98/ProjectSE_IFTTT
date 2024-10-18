package com.ccll.projectse_ifttt.Triggers;

public class FileExisTrigCreator extends TriggerCreator {

    /**
     * Crea un nuovo trigger di tipo {FileExistenceTrig} con il path del file che vogliamo individuare.
     *
     * @return un nuovo oggetto {Trigger} che si attiva quando il file specificato esiste.
     */
    @Override
    public Trigger createTrigger(String path) {
        String[] parts = path.split(" ");
        return new FileExistenceTrig(parts[0]+"\\"+parts[1]);
    }

    @Override
    public String getType() {
        return "file existence";
    }
}
