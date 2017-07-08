package co.uglytruth.lindy.walmart.tag;

/**
 * Created by tjw127 on 7/4/17.
 */

public enum  WTActivityResultTags {

    WTSearch_Result_Status_OK(205);

    private int type;

    private WTActivityResultTags(final int type)
    {
        this.type = type;
    }

    public int getRequestCode()
    {
        return type;
    }
    @Override
    public String toString() {

        return String.valueOf(this.type);
    }
}
