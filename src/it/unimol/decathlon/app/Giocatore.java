package it.unimol.decathlon.app;

import java.io.Serializable;

public class Giocatore implements Comparable<Giocatore>, Serializable {

    private String nome;
    private int punteggio;
    private int tempPunteggio;


    public Giocatore(String nome) {
        this.nome = nome;
        this.punteggio = 0;
        this.tempPunteggio = 0;

    }

    public void addPunteggio(int punti) {
        this.punteggio += punti;
    }

    public void addTempPunteggio(int punti) {
        this.tempPunteggio += punti;
    }

    public void setTempPunteggio(int punti) {
        this.tempPunteggio = punti;
    }

    public String getNome() {
        return this.nome;
    }

    public int getPunteggio() {
        return this.punteggio;
    }

    public int getTempPunteggio() {
        return this.tempPunteggio;
    }

    @Override
    public String toString() { return this.nome + ": " + this.punteggio; }
    public String tempString() { return this.nome + ": " + this.tempPunteggio; }


    @Override
    public int compareTo(Giocatore g) {
        int comparePunt = ((Giocatore) g).punteggio - this.punteggio;
        if (comparePunt == 0)
            return this.nome.compareTo(g.nome);
        return comparePunt;
    }


}
