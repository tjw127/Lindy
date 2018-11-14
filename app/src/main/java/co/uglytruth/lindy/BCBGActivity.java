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

public class BCBGActivity extends AppCompatActivity implements MoPubView.BannerAdListener{

    WebView webView;
    MoPubView moPubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcbg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String url = "https://click.linksynergy.com/fs-bin/click?id=TYRkFi56cOk&offerid=498771.64&type=3&subid=0";


        moPubView = (MoPubView)findViewById(R.id.adview);

        moPubView.setAdUnitId("abe4e322daee4f3fbccf7e2b56318af6");
        moPubView.setBannerAdListener(this);// Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();
        webView = (WebView)findViewById(R.id.bcbgwebview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Reloading page", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                webView.reload();
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
