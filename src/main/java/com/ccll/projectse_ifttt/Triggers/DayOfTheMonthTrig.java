package com.ccll.projectse_ifttt.Triggers;

import java.time.LocalDate;
import java.time.MonthDay;
/**
 * Rappresenta un trigger che si attiva al giorno specificato del mese.
 * Questo trigger valuta se il giorno del mese corrente corrisponde a quello specificato.
 */
public class DayOfTheMonthTrig implements Trigger{

    MonthDay dayOfMonth;
    /**
     * Costruisce un DayOfTheMonthTrig con con il giorno del mese specificato.
     *
     * @param dayOfMonth il giorno del mese specificato per cui questo trigger deve attivarsi.
     */
    public DayOfTheMonthTrig(MonthDay dayOfMonth){
        this.dayOfMonth = dayOfMonth;
    }
    /**
     * Restituisce il giorno del mese specificato per questo trigger.
     *
     * @return il giorno del mese alla quale questo trigger si attiva.
     */
    public MonthDay getDayOfMonth(){return dayOfMonth;}
    /**
     * Imposta un nuovo giorno del mese specificato per questo trigger.
     *
     * @param dayOfMonth il nuovo giorno del mese alla quale questo trigger deve attivarsi.
     */
    public void setDayOfMonth(MonthDay dayOfMonth){this.dayOfMonth = dayOfMonth;}

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se il giorno del mese corrente è uguale a quello specificato.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        LocalDate todayInt =  LocalDate.now();
        MonthDay today = MonthDay.of(todayInt.getMonth(), todayInt.getDayOfMonth());
        return dayOfMonth.getDayOfMonth() == today.getDayOfMonth();
    }
    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica il giorno del mese  alla quale il trigger si attiva.
     */
    @Override
    public String toString() {return "Trigger attivato a: " + dayOfMonth.getDayOfMonth();}
}
