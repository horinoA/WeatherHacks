package com.example.horinoa.weatherhacks;

import android.app.SearchManager;
import android.content.Context;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.example.horinoa.weatherhacks.R.string.ObservationPoint;

public class MainActivity extends AppCompatActivity{

    private TextView city;
    private TextView telop;
    private TextView temperature;
    private TextView tomorrowData;
    private TextView tomorrowTelop;
    private TextView tomorrowTemperture;
    private TextView dayafterData;
    private TextView dayafterTelop;
    private TextView dayafterTemperture;
    private TextView overView;
    private SearchView mSearchView;
    private Menu mainmenu;
    private FunctionMainActivity fMainAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        city = (TextView)findViewById(R.id.city);
        telop = (TextView)findViewById(R.id.telop);
        temperature = (TextView)findViewById(R.id.temperature);
        tomorrowData = (TextView)findViewById(R.id.tomorrowDate);
        tomorrowTelop = (TextView)findViewById(R.id.tomorrowTelop);
        tomorrowTemperture = (TextView)findViewById(R.id.tomorrowTemperature);
        dayafterData = (TextView)findViewById(R.id.dayAfterDate);
        dayafterTelop = (TextView)findViewById(R.id.dayAfterTelop);
        dayafterTemperture = (TextView)findViewById(R.id.dayAfterTemperature);
        overView = (TextView)findViewById(R.id.overView);

        RestAPI r = new RestAPI();
        fMainAct = new FunctionMainActivity(r);

        fMainAct.convertObservationPoints(getResources().getString(R.string.ObservationPoint));
        fMainAct.weatherEntitySet("130010", this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainmenu = menu;
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //searchItem.setIcon(R.drawable.ic_search_white_36dp);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint(getString(R.string.action_serch));
        final MainActivity v = this;
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String namelike) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(final String query) {
                mSearchView.clearFocus();
                //ここからSerchVie処理書く
                fMainAct.serchPoint(query,v);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if (id == R.id.gps_serch){
            return true;
        }*/
        if (id == R.id.help) {
            return true;
        } else if (id == R.id.end) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //物理キー押した時強制的にメニュー表示
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        final int action = event.getAction();
        final int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            // メニュー表示
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                if (mainmenu != null) {
                    mainmenu.performIdentifierAction(R.id.action_settings, 0);
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public TextView getCity() {
        return city;
    }

    public TextView getTelop() {
        return telop;
    }

    public TextView getTemperature() {
        return temperature;
    }

    public TextView getTomorrowData() {
        return tomorrowData;
    }

    public TextView getTomorrowTelop() {
        return tomorrowTelop;
    }

    public TextView getTomorrowTemperture() {
        return tomorrowTemperture;
    }

    public TextView getDayafterData() {
        return dayafterData;
    }

    public TextView getDayafterTelop() {
        return dayafterTelop;
    }

    public TextView getDayafterTemperture() {
        return dayafterTemperture;
    }

    public TextView getOverView() {
        return overView;
    }

    public SearchView getmSearchView() {
        return mSearchView;
    }

}
