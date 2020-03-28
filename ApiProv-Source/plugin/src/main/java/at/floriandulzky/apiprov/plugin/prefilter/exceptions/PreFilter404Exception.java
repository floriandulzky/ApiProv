package at.floriandulzky.apiprov.plugin.prefilter.exceptions;

public class PreFilter404Exception  extends PreFilterException {

    @Override
    public int getStatus() {
        return 404;
    }

    public PreFilter404Exception(String message){
        super(message);
    }
}
