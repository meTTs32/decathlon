package it.unimol.decathlon.app.exceptions;

public class DuplicatePlayerException extends Exception {
    public DuplicatePlayerException() {
        super("Giocatore già presente");
    }
}

