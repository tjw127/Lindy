package co.uglytruth.lindy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

public class KindleActivity extends AppCompatActivity implements MoPubView.BannerAdListener {

    WebView webview;
    MoPubView moPubView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String url = "https://www.amazon.com/gp/search?ie=UTF8&tag=snazzyswag27-20&linkCode=ur2&linkId=620788959d91389879e0074cb25d04f8&camp=1789&creative=9325&index=digital-text&keywords=Women%20success";


        moPubView = (MoPubView)findViewById(R.id.adview);

        moPubView.setAdUnitId("abe4e322daee4f3fbccf7e2b56318af6");
        moPubView.setBannerAdListener(this);// Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();
        webview = (WebView)findViewById(R.id.kindlewebview);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(url);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Reloading page", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                webview.reload();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {

    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }
}
