package co.uglytruth.lindy.walmart.start;

/**
 * Created by tjw127 on 6/29/17.
 */

public class WTStartTracking {

    private Integer startRange;

    private Integer endRange;

    private String json;

    public void setJson(String aJson)
    {
        json = aJson;
    }

    public void setStartRange(int aStartRange)
    {
        startRange = new Integer(aStartRange);
    }

    public void setEndRange(int aEndRange)
    {
        endRange = new Integer(aEndRange);
    }

    public Integer getStartRange()
    {
        return startRange;
    }

    public Integer getEndRange()
    {
        return endRange;
    }

    public String getJson()
    {
        return json;
    }
}
