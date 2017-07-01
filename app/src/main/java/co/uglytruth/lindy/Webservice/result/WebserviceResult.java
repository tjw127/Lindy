package co.uglytruth.lindy.Webservice.result;

/**
 * Created by tjw127 on 6/26/17.
 */

public class WebserviceResult {

    private int type;

    private String result;


    public void setType(int aType)
    {
        this.type = aType;
    }

    public void setResult(String aResult)
    {
        this.result = aResult;
    }

    public int getType()
    {
        return this.type;
    }

    public String getResult()
    {
        return this.result;
    }
}
