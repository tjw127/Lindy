package co.uglytruth.lindy.walmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.walmartlabs.tofa.WalmartBuyNowButton;
import com.walmartlabs.tofa.WalmartItem;
import com.walmartlabs.tofa.WalmartItemIdType;
import com.walmartlabs.tofa.WalmartLineItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

import co.uglytruth.lindy.R;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.holder.WalmartViewHolder;
import co.uglytruth.lindy.walmart.tag.WalmartTags;

/**
 * Created by tjw127 on 6/24/17.
 */

public class WalmartAdapter {

    private WTSearch search;

    private Context context;

    private int id;

    WTSearch.Items[] items;


    public WTAdapter getAdapter(WTSearch.Items[] aItems, Context aContext)
    {
        items = aItems;

        context = aContext;

        WTAdapter wtAdapter = new WTAdapter();

        return wtAdapter;
    }
    public class WTAdapter extends RecyclerView.Adapter<WalmartViewHolder>{

        @Override
        public WalmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.walmart_view, parent, false);

            int height = parent.getMeasuredHeight() / 4;

            int width = parent.getMeasuredWidth() / 4;

            itemView.setMinimumHeight(height);

            itemView.setMinimumWidth(width);

            return new WalmartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WalmartViewHolder holder, int position) {

            id = position;

            holder.setIsRecyclable(false);

            WTSearch.Items item = items[position];


            WalmartItem walmartItem = new WalmartItem(item.itemId, WalmartItemIdType.WALMART_ID);


            Integer qualityInteger = null;

            //items.stock.equals("Available")

            if (item.availableOnline == true)
            {
                qualityInteger = new Integer(1);
            }else {

                qualityInteger = new Integer(0);
            }

            //holder.walmartBuyNowButton.se
            holder.walmartBuyNowButton.addItem(walmartItem, qualityInteger.intValue());

            if (qualityInteger.intValue() == 0) {

                holder.walmartPriceTextView.setText("$" + "0.00");

                holder.walmartPriceTextView.setTextColor(Color.RED);

            }else {

                Float itemFloat = new Float(item.salePrice);

                holder.walmartPriceTextView.setText("$" + String.format("%.2f", itemFloat.floatValue()));
            }



            Picasso.with(context).load(item.largeImage)
                    .into(holder.walmartImageView);
        }

        @Override
        public int getItemCount() {
            if (items != null)
            {
                return items.length;
            }else {

                return 0;
            }

        }
    }
}
