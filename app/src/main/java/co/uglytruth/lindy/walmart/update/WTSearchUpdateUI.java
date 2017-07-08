package co.uglytruth.lindy.walmart.update;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.adapter.WalmartAdapter;

/**
 * Created by tjw127 on 7/3/17.
 */

public class WTSearchUpdateUI implements Runnable {

    private boolean isAdapterNull;

    private WTSearch.Items[] items;

    private RecyclerView recyclerView;

    private Context context;

    public static class Builder{

        private WalmartAdapter.WTAdapter adapter;

        private RecyclerView recyclerView;

        private WTSearch.Items[] items;

        private Context context;


        public Builder()
        {

        }

        public Builder adapter(WalmartAdapter.WTAdapter aAdapter)
        {
            this.adapter = aAdapter;

            return this;
        }

        public Builder context(Context aContext)
        {
            this.context = aContext;

            return this;
        }

        public Builder recyclerView(RecyclerView aRecyclerView)
        {
            this.recyclerView = aRecyclerView;

            return this;
        }

        public Builder items(WTSearch.Items[] aItems)
        {
            this.items = aItems;

            return this;
        }


        public WTSearchUpdateUI build()
        {

            return new WTSearchUpdateUI(this);
        }
    }

    public WTSearchUpdateUI(Builder builder)
    {
        if (builder.items != null)
        {


            try{

                if (builder.adapter != null)
                {
                    this.isAdapterNull = false;

                    builder.adapter.notifyDataSetChanged();
                }else {

                    this.isAdapterNull = true;

                    throw new NullPointerException();
                }


            }catch (NullPointerException e)
            {
                this.isAdapterNull = true;

                e.printStackTrace();

            }finally {


                switch (Boolean.toString(isAdapterNull))
                {
                    case "true":

                        this.context = builder.context;

                        this.items = builder.items;

                        this.recyclerView = builder.recyclerView;

                        run();

                        break;

                    case "false":

                        break;

                    default:

                        break;


                }
            }

        }
    }
    @Override
    public void run() {

        WalmartAdapter adapter = new WalmartAdapter();

        WalmartAdapter.WTAdapter wtAdapter = adapter.getAdapter(this.items, this.context);

        this.recyclerView.setAdapter(wtAdapter);


    }
}
