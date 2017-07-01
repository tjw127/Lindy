package co.uglytruth.lindy.walmart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WTSearch {


    @SerializedName("query")
    public String query;

    @SerializedName("sort")
    public String sort;

    @SerializedName("format")
    public String format;

    @SerializedName("responseGroup")
    public String responseGroup;


    @SerializedName("totalResults")
    public String totalResults;

    @SerializedName("start")
    public String start;

    @SerializedName("numItems")
    public String numItems;

    @SerializedName("items")
    public Items[] items;

    public static class Items{

        @SerializedName("itemId")
        public String itemId;

        @SerializedName("parentItemId")
        public String parentItemId;

        @SerializedName("stock")
        public String stock;

        @SerializedName("addToCartUrl")
        public String addToCartUrl;

        @SerializedName("affiliateAddToCartUrl")
        public String affiliateAddToCartUrl;

        @SerializedName("largeImage")
        public String largeImage;

        @SerializedName("mediumImage")
        public String mediumImage;

        @SerializedName("name")
        public String name;

        @SerializedName("offerType")
        public String offerType;

        @SerializedName("isTwoDayShippingEligible")
        public boolean isTwoDayShippingEligible;

        @SerializedName("upc")
        public boolean upc;

        @SerializedName("salePrice")
        public String salePrice;

        @SerializedName("categoryPath")
        public String categoryPath;

        @SerializedName("shortDescription")
        public String shortDescription;

        @SerializedName("thumbnailImage")
        public String thumbnailImage;

        @SerializedName("productTrackingUrl")
        public String productTrackingUrl;

        @SerializedName("standardShipRate")
        public String standardShipRate;

        @SerializedName("marketplace")
        public String marketplace;

        @SerializedName("productUrl")
        public String productUrl;

        @SerializedName("numReviews")
        public String numReviews;

        @SerializedName("customerRating")
        public String customerRating;

        @SerializedName("customerRatingImage")
        public String customerRatingImage;

        @SerializedName("categoryNode")
        public String categoryNode;

        @SerializedName("bundle")
        public boolean bundle;

        @SerializedName("availableOnline")
        public boolean availableOnline;



    }


}
