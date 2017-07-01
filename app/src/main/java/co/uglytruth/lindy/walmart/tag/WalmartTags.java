package co.uglytruth.lindy.walmart.tag;

/**
 * Created by tjw127 on 6/24/17.
 */

public enum  WalmartTags {

    ITEM_WALMART_ID_TAG("<Item_walmart_id>"), ITEM_WALMART_UPC("<Item_upc>");

    private String text;

    private WalmartTags(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString() {

        return text;
    }
}
