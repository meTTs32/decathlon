package it.unimol.decathlon.app;

import it.unimol.decathlon.app.exceptions.VoidNameException;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

    /**
     * Classe che rappresenta un giocatore.
     */

    private final String name;
    private int points;
    private int tempPoints;

    /**
     * Costruttore della classe giocatore.
     *
     * @param name nome del giocatore.
     * @throws VoidNameException se il nome è vuoto.
     */
    public Player(String name) throws VoidNameException {
        if(name.isEmpty())
            throw new VoidNameException();
        else {
            this.name = name;
            this.points = 0;
            this.tempPoints = 0;
        }
    }


    /**
     * Aggiunge al giocatore i punti passati come parametro.
     *
     * @param punti punti da aggiungere al giocatore.
     */
    public void addScore(int punti) {
        this.points += punti;
    }


    /**
     * Aggiunge al giocatore i punti nella classifica della singola disciplina.
     *
     * @param punti punti da impostare al giocatore.
     */
    public void addTempScore(int punti) {
        this.tempPoints += punti;
    }


    /**
     * Imposta al giocatore i punti nella classifica della singola disciplina.
     * A differenza di {@link #addTempScore(int)}, questo metodo non aggiunge i punti ma li imposta.
     *
     * @param punti punti da impostare al giocatore.
     */
    public void setTempScore(int punti) {
        this.tempPoints = punti;
    }


    /**
     * Restituisce i punti della singola disciplina del giocatore.
     *
     * @return punti del giocatore.
     */
    public int getTempScore() {
        return this.tempPoints;
    }

    /**
     * Restituisce il nome del giocatore.
     * @return nome del giocatore.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Restituisce una stringa contenente il nome del giocatore e il suo punteggio totale.
     * @return stringa contenente il nome del giocatore e il suo punteggio totale.
     */
    @Override
    public String toString() { return this.name + " : " + this.points; }


    /**
     * Restituisce una stringa contenente il nome del giocatore e il suo punteggio nella singola disciplina.
     * @return stringa contenente il nome del giocatore e il suo punteggio nella singola disciplina.
     */
    public String tempString() { return this.name + " : " + this.tempPoints; }


    /**
     * Restituisce un valore (positivo, nullo o negativo) che rappresenta il confronto tra i punteggi di questo giocatore
     * e del giocatore passato come parametro. Serve per ordinare le classifiche in ordine decrescente di punti. Nel caso
     * due giocatori abbiano lo stesso punteggio, vengono ordinati in ordine alfabetico.
     *
     * @param g the object to be compared.
     * @return un valore intero maggiore, minore o uguale a zero che determina se il punteggio di un giocatore è maggiore,
     * minore o uguale del punteggio di un altro giocatore.
     */
    @Override
    public int compareTo(Player g) {
        int comparePunt = g.points - this.points;
        if (comparePunt == 0)
            return this.name.compareTo(g.name);
        return comparePunt;
    }


}
