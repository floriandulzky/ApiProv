package at.floriandulzky.apiprov.plugin.prefilter.exceptions;

public class PreFilter500Exception extends PreFilterException {

    @Override
    public int getStatus() {
        return 500;
    }

    public PreFilter500Exception(String message){
        super(message);
    }

}
