package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Questa classe rappresenta una regola periodica che estende la classe Rule.
 * Una regola periodica si attiva a intervalli regolari definiti da un periodo.
 */
public class PeriodicRule extends Rule {
    private final Duration period;
    private LocalDateTime endPeriod;

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
        this.period = parsePeriod(period);
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

    /**
     * Aggiorna lo stato della regola ed esegue il metodo evaluateTrigger definito nella classe Rule
     * @return il risultato del metodo evaluateTrigger definito nella classe Rule
     */
    @Override
    public boolean evaluateTrigger() {
        updateState();
        return super.evaluateTrigger();
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
        endPeriod = getCurrentDateTime().plus(period);
    }

    /**
     * Aggiorna lo stato della regola.
     * Se la regola è attiva e la variabile endPeriod è diversa da null, la imposta a null.
     * Se la regola è disattivata e la variabile endPeriod è diversa da null e il giorno e l'ora corrente sono uguali alla variabile endPeriod, imposta lo stato della regola a true.
     * <p></p>
     * Casi d'uso considerati:
     * <li>CASO 1: regola attiva --> trigger verificato --> azione eseguita --> regola non attiva --> trascorre il period --> regola attiva --> ... </li>
     * <li>CASO 2: regola attiva --> utente disattiva regola --> regola resta non attiva --> utente riattiva regola --> caso 1</li>
     * <li>CASO 3: regola attiva --> trigger verificato --> azione eseguita --> regola non attiva --> utente riattiva regola --> caso 1</li>
     */
    private void updateState() {
        if (super.isState() && endPeriod != null) {
            endPeriod = null;
        }
        if (!super.isState() && endPeriod != null && endPeriod.isEqual(getCurrentDateTime())) {
            super.setState(true);
        }
    }

    /**
     * Restituisce la data e l'ora corrente più il periodo della regola,
     * con secondi e nanosecondi impostati a zero.
     *
     * @return La data e l'ora corrente più il periodo
     */
    private  LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now().withSecond(0).withNano(0);
    }

}
