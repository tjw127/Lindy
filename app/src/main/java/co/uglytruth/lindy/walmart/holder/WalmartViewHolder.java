package co.uglytruth.lindy.walmart.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.walmartlabs.tofa.WalmartBuyNowButton;

import co.uglytruth.lindy.R;
import co.uglytruth.lindy.walmart.button.ShoppingBuyButton;

/**
 * Created by tjw127 on 6/24/17.
 */

public class WalmartViewHolder extends RecyclerView.ViewHolder{

    public ImageView walmartImageView;

    public TextView walmartPriceTextView;

   // public ImageButton walmartImageButton;

    public ShoppingBuyButton shoppingBuyButton;

    public WalmartViewHolder(View itemView) {
        super(itemView);

        walmartImageView = (ImageView)itemView.findViewById(R.id.walmartImageView);

        //walmartImageButton = (ImageButton)itemView.findViewById(R.id.walmartImageButton);
        walmartPriceTextView = (TextView)itemView.findViewById(R.id.walmartPriceTextView);

        shoppingBuyButton = (ShoppingBuyButton)itemView.findViewById(R.id.shopping_buy_button);
//        walmartBuyNowButton = (WalmartBuyNowButton)itemView.findViewById(R.id.walmartBuyButton);


    }
}
