package at.floriandulzky.apiprov.plugin.prefilter.exceptions;

public class PreFilter405Exception extends PreFilterException {

    @Override
    public int getStatus() {
        return 405;
    }

    public PreFilter405Exception(String message){
        super(message);
    }
}
