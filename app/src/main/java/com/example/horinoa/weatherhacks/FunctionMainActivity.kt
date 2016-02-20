package com.example.horinoa.weatherhacks

import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import kotlin.properties.Delegates
import java.lang.Math.*
/**
 * Created by horinoA on 2016/02/02.
 */
class FunctionMainActivity(val delegate:WeatherHackable) : WeatherHackable by delegate {

    var obserblePoint: ObservationPoint by Delegates.notNull()

    fun weatherEntitySet(city: String, v: MainActivity) {
        childViewInit(v)
        delegate.retroFitCall(city, {callback ->
            callback?.let {
                v.city.setText(it.title.replace("の天気", "", false))
                v.overView.setText(it.description.text)
            }
            try{
            callback?.forecasts?.get(0).let{
                v.telop.setText(it?.telop)
                val min = if(it?.temperature?.min?.celsius != null){
                    it?.temperature?.min?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                }else{
                    v.getResources().getString(R.string.celsiusNull)
                }
                val max = if(it?.temperature?.max?.celsius != null){
                    it?.temperature?.max?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                }else{
                    v.getResources().getString(R.string.celsiusNull)
                }
                if (min == v.getResources().getString(R.string.celsiusNull) && max == v.getResources().getString(R.string.celsiusNull)) {
                    v.temperature.setVisibility(View.GONE)
                } else {
                    v.temperature.setVisibility(View.VISIBLE)
                    v.temperature.setText(min + "/" + max)
                }
            }
            }catch(e:IndexOutOfBoundsException){
                Log.d("error","forecasts(0)error")
            }
            try{
                callback?.forecasts?.get(1).let {
                v.tomorrowData.setText(it?.date)
                v.tomorrowTelop.setText(it?.telop)
                val tomorrowmin = if(it?.temperature?.min?.celsius != null){
                    it?.temperature?.min?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                }else{
                    v.getResources().getString(R.string.celsiusNull)
                }
                val tomorrowmax = if(it?.temperature?.max?.celsius != null){
                    it?.temperature?.max?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                }else{
                    v.getResources().getString(R.string.celsiusNull)
                }
                v.tomorrowTemperture.setText(tomorrowmin + "/" + tomorrowmax)
            }
            }catch(e:IndexOutOfBoundsException){
                Log.d("error","forecasts(1)error")
            }
            try {
                callback?.forecasts?.get(2).let {
                    v.dayafterData.setText(it?.date)
                    v.dayafterTelop.setText(it?.telop)
                    val dayaftermin = if (it?.temperature?.min?.celsius != null) {
                        it?.temperature?.min?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                    } else {
                        v.getResources().getString(R.string.celsiusNull)
                    }
                    val dayaftermax = if (it?.temperature?.max?.celsius != null) {
                        it?.temperature?.max?.celsius?.toString() + v.getResources().getString(R.string.celsiusUnit)
                    } else {
                        v.getResources().getString(R.string.celsiusNull)
                    }
                    v.dayafterTemperture.setText(dayaftermin + "/" + dayaftermax)
                }
            }catch(e:IndexOutOfBoundsException){
                Log.d("error","forecasts(2)error")
            }
        })
    }

    fun childViewInit(v:MainActivity){
        v.city.setText(v.getResources().getString(R.string.celsiusNull))
        v.telop.setText(v.getResources().getString(R.string.celsiusNull))
        v.temperature.setText(v.getResources().getString(R.string.celsiusNull))
        v.tomorrowData.setText(v.getResources().getString(R.string.celsiusNull))
        v.tomorrowTelop.setText(v.getResources().getString(R.string.celsiusNull))
        v.tomorrowTemperture.setText(v.getResources().getString(R.string.celsiusNull))
        v.dayafterData.setText(v.getResources().getString(R.string.celsiusNull))
        v.dayafterTelop.setText(v.getResources().getString(R.string.celsiusNull))
        v.dayafterTemperture.setText(v.getResources().getString(R.string.celsiusNull))
        v.overView.setText(v.getResources().getString(R.string.celsiusNull))
    }

    fun convertObservationPoints(str: String) {
        val gson: Gson = Gson()
        obserblePoint = gson.fromJson(str, ObservationPoint::class.java)
    }

    fun serchPoint(query:String,v:MainActivity){
        delegate.getGeocode(query,v.applicationContext,{position ->
            val r = 6378.137 // 赤道半径[km]
            if(position != null){
                val positionLat = position.lat * PI /180
                val positionLng = position.lng * PI /180
                obserblePoint.component1().forEach {
                    val endLat = it.lat * PI / 180
                    val endLng = it.lng * PI / 180
                    it.distance = r * acos(sin(positionLat) * sin(endLat) + cos(positionLat) * cos(endLat) * cos(endLng - positionLng))
                }
                val returnPosition = obserblePoint.component1().minBy { it.distance }
                Log.d("return",returnPosition.toString())
                weatherEntitySet(returnPosition?.id ?: "",v)
            }else{
                childViewInit(v)
            }
        })
    }

}
