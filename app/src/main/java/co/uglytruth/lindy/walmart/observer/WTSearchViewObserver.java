package co.uglytruth.lindy.walmart.observer;

import android.support.v7.widget.RecyclerView;

import java.util.Observable;
import java.util.Observer;

import co.uglytruth.lindy.walmart.observable.WTSearchList;

/**
 * Created by tjw127 on 6/26/17.
 */

public class WTSearchViewObserver implements Observer {

    public RecyclerView aView;

    public WTSearchViewObserver(RecyclerView recyclerView)
    {
        this.aView = recyclerView;
    }

    public static void main(String[] args)
    {
        /*
        RecyclerView recyclerView = n
        WTSearchList wtSearchList = new WTSearchList(aV)
        */
    }
    @Override
    public void update(Observable o, Object arg) {

    }
}
