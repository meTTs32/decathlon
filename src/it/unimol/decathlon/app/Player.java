package it.unimol.decathlon.app;

import it.unimol.decathlon.app.exceptions.VoidNameException;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

    private String nome;
    private int points;
    private int tempPoints;


    public Player(String nome) throws VoidNameException {
        if(nome.isEmpty())
            throw new VoidNameException();
        else {
            this.nome = nome;
            this.points = 0;
            this.tempPoints = 0;
        }
    }

    public void addPoints(int punti) {
        this.points += punti;
    }

    public void addTempPoints(int punti) {
        this.tempPoints += punti;
    }

    public void setTempPoints(int punti) {
        this.tempPoints = punti;
    }

    public int getTempPoints() {
        return this.tempPoints;
    }

    public String getName() {
        return this.nome;
    }

    @Override
    public String toString() { return this.nome + ": " + this.points; }
    public String tempString() { return this.nome + ": " + this.tempPoints; }


    @Override
    public int compareTo(Player g) {
        int comparePunt = g.points - this.points;
        if (comparePunt == 0)
            return this.nome.compareTo(g.nome);
        return comparePunt;
    }


}
