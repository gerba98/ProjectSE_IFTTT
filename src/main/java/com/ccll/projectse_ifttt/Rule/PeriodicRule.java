package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Questa classe rappresenta una regola periodica che estende la classe Rule.
 * Una regola periodica si attiva a intervalli regolari definiti da un periodo.
 */
public class PeriodicRule extends Rule {
    private final Duration period;
    private Duration endPeriod;
    private String strPeriod;


    private BooleanProperty reactivated;
    private boolean test;




    /**
     * Costruttore della classe PeriodicRule.
     * Crea una nuova istanza di PeriodicRule dato il nome, il trigger, l'azione e il periodo specificati.
     * Lo stato della regola è inizializzato a true e il numero di esecuzioni a 0.
     *
     * @param name    Il nome della regola
     * @param trigger Il trigger della regola
     * @param action  L'azione della regola
     * @param period  Il periodo della regola in formato stringa (dd:hh:mm)
     */
    public PeriodicRule(String name, Trigger trigger, Action action, String period) {
        super(name, trigger, action);
        this.strPeriod = period;
        this.period = parsePeriod(period).minus(1, ChronoUnit.SECONDS);
        this.reactivated = new SimpleBooleanProperty(true);
        this.test = false;
    }

    public void initEndPeriod(){
        endPeriod=period;
    }
    /**
     * Converte una stringa rappresentante un periodo in un oggetto Duration.
     *
     * @param time Il periodo in formato stringa (dd:hh:mm)
     * @return Un oggetto Duration rappresentante il periodo
     */
    private static Duration parsePeriod(String time) {
        int days = 0, hours = 0, minutes = 0;

        String[] tokens = time.split(":");
        if (tokens.length == 3) {
            days = Integer.parseInt(tokens[0]);
            hours = Integer.parseInt(tokens[1]);
            minutes = Integer.parseInt(tokens[2]);
        }

        return Duration.ofDays(days)
                .plus(hours, ChronoUnit.HOURS)
                .plus(minutes, ChronoUnit.MINUTES);
    }

    /**
     * Restituisce il tipo della regola.
     *
     * @return Il tipo della regola
     */
    @Override
    public String getType() {
        return "PeriodicRule";
    }


    public String getStrPeriod() {
        return strPeriod;
    }
    /**
     * Aggiorna lo stato della regola ed esegue il metodo evaluateTrigger definito nella classe Rule
     *
     * @return il risultato del metodo evaluateTrigger definito nella classe Rule
     */
    @Override
    public boolean evaluateTrigger() {
        updateState();
        return super.evaluateTrigger();
    }

    public BooleanProperty reactivatedProperty() {
        return reactivated;
    }


    /**
     * Esegue il metodo executeAction definito nella classe Rule.
     * Imposta lo stato della regola a false.
     * Assegna alla variabile endPeriod la data e l'ora corrente più il periodo della regola.
     */
    @Override
    public void executeAction() {
        super.executeAction();
        super.setState(false);
        endPeriod = period;
    }

    @Override
    public String toString() {
        return super.getName() + ";" + super.getTrigger() + ";" + super.getAction() + ";" + isState() + ";" + getType()+ "-" + strPeriod + ";" +reactivated;
    }

    /**
     * Restituisce lo stato di riattivazione della regola periodica.
     *
     * @return true se la regola sarà riattivata, false altrimenti
     */
    public boolean isReactivated() {
        return reactivated.get();
    }

    /**
     * Imposta lo stato di riattivazione della regola periodica.
     *
     * @param reactivated false per disattivare la riattivazione della regola, true per attivarla
     */
    public void setReactivated(boolean reactivated) {
        this.reactivated.set(reactivated);
    }

    /**
     * Aggiorna lo stato della regola.
     * Se la regola è attiva e la variabile endPeriod è diversa da null, la imposta a null.
     * Se la regola è disattivata e la variabile endPeriod è diversa da null e il giorno e l'ora corrente sono uguali alla variabile endPeriod, imposta lo stato della regola a true.
     * <p></p>
     * Casi d'uso considerati:
     * <li>CASO 1: (funzionamento base) regola attiva --> trigger verificato --> azione eseguita --> regola non attiva --> trascorre il period --> regola attiva --> ... </li>
     * <li>CASO 2: (se l'utente disattiva la regola essa rimane disattivata) regola attiva/non attiva --> utente disattiva regola --> regola resta non attiva --> utente riattiva regola --> caso 1</li>
     * <li>CASO 3: (se l'utente riattiva la regola endPeriod viene azzerato) regola attiva --> trigger verificato --> azione eseguita --> regola non attiva --> utente riattiva regola --> caso 1</li>
     */
    private void updateState() {
        if(endPeriod!=null) {
            // CASO 3
            if (super.isState()) {
                endPeriod = null;
                reactivated.set(true);
                return;
            }else{
                // CASO 2
                if(!reactivated.get()){
                    endPeriod=null;
                    return;
                }
                // CASO 1
                if(endPeriod == Duration.ZERO) {
                    super.setState(true);
                }else{
                    endPeriod = endPeriod.minus(1, ChronoUnit.SECONDS);
                }

            }

        }
    }

}
