package com.example.horinoa.weatherhacks

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    var city: TextView by Delegates.notNull()
    var telop: TextView by Delegates.notNull()
    var temperature: TextView by Delegates.notNull()
    var tomorrowData: TextView by Delegates.notNull()
    var tomorrowTelop: TextView by Delegates.notNull()
    var tomorrowTemperture: TextView by Delegates.notNull()
    var dayafterData: TextView by Delegates.notNull()
    var dayafterTelop: TextView by Delegates.notNull()
    var dayafterTemperture: TextView by Delegates.notNull()
    var overView: TextView by Delegates.notNull()
    var mSearchView: SearchView by Delegates.notNull()
    var mainmenu: Menu by Delegates.notNull()
    var fMainAct: FunctionMainActivity by Delegates.notNull()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        city = findViewById(R.id.city) as TextView
        telop = findViewById(R.id.telop) as TextView
        temperature = findViewById(R.id.temperature) as TextView
        tomorrowData = findViewById(R.id.tomorrowDate) as TextView
        tomorrowTelop = findViewById(R.id.tomorrowTelop) as TextView
        tomorrowTemperture = findViewById(R.id.tomorrowTemperature) as TextView
        dayafterData = findViewById(R.id.dayAfterDate) as TextView
        dayafterTelop = findViewById(R.id.dayAfterTelop) as TextView
        dayafterTemperture = findViewById(R.id.dayAfterTemperature) as TextView
        overView = findViewById(R.id.overView) as TextView

        val r = RestAPI()
        fMainAct = FunctionMainActivity(r)

        fMainAct.convertObservationPoints(resources.getString(R.string.ObservationPoint))
        fMainAct.weatherEntitySet("130010", this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        mainmenu = menu
        val searchItem = menu.findItem(R.id.action_search)
        //searchItem.setIcon(R.drawable.ic_search_white_36dp);
        mSearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        mSearchView.queryHint = getString(R.string.action_serch)
        val v = this
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(namelike: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchView.clearFocus()
                //ここからSerchVie処理書く
                fMainAct.serchPoint(query, v)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }
        /*if (id == R.id.gps_serch){
            return true;
        }*/
        if (id == R.id.help) {
            return true
        } else if (id == R.id.end) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
    }

    //物理キー押した時強制的にメニュー表示
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action = event.action
        val keyCode = event.keyCode
        if (action == KeyEvent.ACTION_UP) {
            // メニュー表示
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                if (mainmenu != null) {
                    mainmenu.performIdentifierAction(R.id.action_settings, 0)
                }
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    /*fun getmSearchView(): SearchView {
        return mSearchView
    }*/

}
