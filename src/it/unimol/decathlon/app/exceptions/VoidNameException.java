package it.unimol.decathlon.app.exceptions;

public class VoidNameException extends Exception {
    public VoidNameException() {
        super("Nome vuoto");
    }
}

