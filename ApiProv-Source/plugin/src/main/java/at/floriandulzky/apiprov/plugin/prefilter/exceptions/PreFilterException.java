package at.floriandulzky.apiprov.plugin.prefilter.exceptions;

import at.floriandulzky.apiprov.plugin.PluginException;

public abstract class PreFilterException extends PluginException {

    public abstract int getStatus();

    protected PreFilterException(String message){
        super(message);
    }

}
