package co.uglytruth.lindy.Webservice.type;

/**
 * Created by tjw127 on 6/26/17.
 */

public enum WebserviceCollectionType {

    WTSEARCHCOLLECTION(1), WTTAXONOMYCOLLLECTION(2);

    private int type;

    private WebserviceCollectionType(final int type)
    {
        this.type = type;
    }

    public int getValue()
    {
        return this.type;
    }

    @Override
    public String toString()
    {
        return new Integer(this.type).toString();
    }
}
