package co.uglytruth.lindy.Webservice.callback;

/**
 * Created by tjw127 on 6/25/17.
 */

public interface WebserviceCallback {

    public void serviceComplete(boolean sucess);

    public void serviceFailure(boolean failure);
}
