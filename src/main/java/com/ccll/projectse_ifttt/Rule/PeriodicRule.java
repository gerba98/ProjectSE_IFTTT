package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Questa classe rappresenta una regola periodica che estende la classe {@link Rule}.
 * Una regola periodica si attiva a intervalli regolari definiti da un periodo, con possibilità di riattivazione automatica.
 */
public class PeriodicRule extends Rule {
    private final Duration period;
    private Duration endPeriod;
    private String strPeriod;
    private BooleanProperty reactivated;
    private boolean test;

    /**
     * Costruisce una nuova istanza di PeriodicRule dato il nome, il trigger, l'azione e il periodo specificati.
     * Lo stato della regola è inizializzato a true e la riattivazione è impostata su true.
     *
     * @param name    il nome della regola
     * @param trigger il trigger della regola
     * @param action  l'azione della regola
     * @param period  il periodo della regola in formato stringa (dd:hh:mm)
     */
    public PeriodicRule(String name, Trigger trigger, Action action, String period) {
        super(name, trigger, action);
        this.strPeriod = period;
        this.period = parsePeriod(period).minus(1, ChronoUnit.SECONDS);
        this.reactivated = new SimpleBooleanProperty(true);
        this.test = false;
    }

    /**
     * Inizializza {@code endPeriod} con il valore del periodo.
     */
    public void initEndPeriod() {
        endPeriod = period;
    }

    /**
     * Converte una stringa rappresentante un periodo in un oggetto {@link Duration}.
     *
     * @param time il periodo in formato stringa (dd:hh:mm)
     * @return un oggetto {@link Duration} rappresentante il periodo
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
     * @return il tipo della regola come stringa
     */
    @Override
    public String getType() {
        return "PeriodicRule";
    }

    /**
     * Restituisce il periodo della regola come stringa.
     *
     * @return una stringa che rappresenta il periodo della regola
     */
    public String getStrPeriod() {
        return strPeriod;
    }

    /**
     * Aggiorna lo stato della regola ed esegue il metodo {@code evaluateTrigger} della classe {@link Rule}.
     *
     * @return il risultato del metodo {@code evaluateTrigger} della classe {@link Rule}
     */
    @Override
    public boolean evaluateTrigger() {
        updateState();
        return super.evaluateTrigger();
    }

    /**
     * Restituisce la proprietà {@code reactivated}, utilizzata per monitorare lo stato di riattivazione.
     *
     * @return un oggetto {@link BooleanProperty} rappresentante la riattivazione
     */
    public BooleanProperty reactivatedProperty() {
        return reactivated;
    }

    /**
     * Esegue l'azione associata alla regola e aggiorna lo stato della regola a "non attiva".
     * Imposta inoltre {@code endPeriod} per monitorare il periodo fino alla prossima attivazione.
     */
    @Override
    public void executeAction() {
        super.executeAction();
        super.setState(false);
        endPeriod = period;
    }

    /**
     * Restituisce una rappresentazione stringa della regola periodica.
     *
     * @return una stringa che rappresenta la regola, con nome, trigger, azione, stato e tipo
     */
    @Override
    public String toString() {
        return super.getName() + ";" + super.getTrigger() + ";" + super.getAction() + ";" + isState() + ";" + getType() + "-" + strPeriod + ";" + reactivated;
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
     * Aggiorna lo stato della regola in base ai seguenti casi:
     * <ul>
     *     <li><b>CASO 1</b>: Se la regola è attiva e il trigger è verificato, esegue l'azione e disattiva la regola.</li>
     *     <li><b>CASO 2</b>: Se la regola è disattivata e {@code reactivated} è false, mantiene la regola disattivata.</li>
     *     <li><b>CASO 3</b>: Se l'utente riattiva la regola mentre è disattiva, azzera {@code endPeriod} per una nuova attivazione.</li>
     * </ul>
     */
    private void updateState() {
        if (endPeriod != null) {
            // CASO 3
            if (super.isState()) {
                endPeriod = null;
                reactivated.set(true);
            } else {
                // CASO 2
                if (!reactivated.get()) {
                    endPeriod = null;
                    return;
                }
                // CASO 1
                if (endPeriod == Duration.ZERO) {
                    super.setState(true);
                } else {
                    endPeriod = endPeriod.minus(1, ChronoUnit.SECONDS);
                }
            }
        }
    }
}
