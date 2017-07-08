package co.uglytruth.lindy.walmart.update;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Set;

import co.uglytruth.lindy.walmart.WTSearch;

/**
 * Created by tjw127 on 7/4/17.
 */

public interface WTSearchUpdateUIInterface {

    public void updateUI(RecyclerView recyclerView, Set<String> resultSet, Context context);
}
