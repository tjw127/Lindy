package co.uglytruth.lindy.Webservice.contenttype;

/**
 * Created by tjw127 on 6/21/17.
 */

public enum ContentType {

    XML("application/xml"), JSON("application/json"),
    X_WWW_FORM_URL_ENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"), HTML("text/html"),
    PLAIN("text/plain"), ATOM("application/atom+xml");

    private String text;

    private ContentType(final String text)
    {
        this.text =  text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
