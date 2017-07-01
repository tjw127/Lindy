package co.uglytruth.lindy;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mapbox.services.android.location.LostLocationEngine;
import com.mapbox.services.android.navigation.v5.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.RouteProgress;
import com.mapbox.services.android.navigation.v5.listeners.AlertLevelChangeListener;
import com.mapbox.services.android.navigation.v5.listeners.NavigationEventListener;
import com.mapbox.services.android.navigation.v5.listeners.OffRouteListener;
import com.mapbox.services.android.navigation.v5.listeners.ProgressChangeListener;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.commons.models.Position;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity implements  ProgressChangeListener, NavigationEventListener, AlertLevelChangeListener, OffRouteListener {


    private MapboxNavigation navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Mapbox.getInstance(this, "pk.eyJ1IjoidGp3MTI3IiwiYSI6ImNqMXR5NG1lMTAwZGQycW1pbzhqYXdtMWIifQ.9BurAlC9tlHFqfDdvwx_rw");
        setContentView(R.layout.activity_navigation);


        navigation = new MapboxNavigation(this, "pk.eyJ1IjoidGp3MTI3IiwiYSI6ImNqMXR5NG1lMTAwZGQycW1pbzhqYXdtMWIifQ.9BurAlC9tlHFqfDdvwx_rw");

        Position origin = Position.fromCoordinates(38.90992, -77.03613);
        Position destination = Position.fromCoordinates(38.8977, -77.0365);

        LocationEngine locationEngine = LostLocationEngine.getLocationEngine(this);

        navigation.setLocationEngine(locationEngine);


        navigation.getRoute(origin, destination, new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigation.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        navigation.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        navigation.removeAlertLevelChangeListener(this);
        navigation.removeNavigationEventListener(this);
        navigation.removeProgressChangeListener(this);
        navigation.removeOffRouteListener(this);

        navigation.endNavigation();
        navigation.onDestroy();
    }

    @Override
    public void onAlertLevelChange(int alertLevel, RouteProgress routeProgress) {

    }

    @Override
    public void onRunning(boolean running) {

    }

    @Override
    public void userOffRoute(Location location) {

    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {

    }
}
