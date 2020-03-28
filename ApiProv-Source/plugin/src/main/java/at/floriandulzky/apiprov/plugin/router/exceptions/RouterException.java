package at.floriandulzky.apiprov.plugin.router.exceptions;

public abstract class RouterException extends Exception {
    protected RouterException(String message){
        super(message);
    }
}
