package co.uglytruth.lindy.walmart.observable;

import android.support.v7.widget.RecyclerView;

import java.util.Observable;

/**
 * Created by tjw127 on 6/26/17.
 */

public class WTSearchList extends Observable{

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    public WTSearchList(RecyclerView aView, RecyclerView.Adapter aAdapter)
    {
        this.adapter = aAdapter;

        this.recyclerView = aView;
    }

    public void setRecyclerView(RecyclerView aRecyclerView)
    {
        if (!this.recyclerView.equals(aRecyclerView))
        {
            this.recyclerView = aRecyclerView;

            setChanged();

            notifyObservers(aRecyclerView);
        }
    }

    public void setAdapter(RecyclerView.Adapter aAdapter)
    {
        if (!this.adapter.equals(aAdapter))
        {

            this.adapter = aAdapter;

            setChanged();

            notifyObservers(aAdapter);
        }
    }
}
