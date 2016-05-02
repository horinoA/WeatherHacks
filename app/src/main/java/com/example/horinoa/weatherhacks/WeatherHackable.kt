package com.example.horinoa.weatherhacks

/**
 * Created by horinoA on 2016/03/25.
 */
import android.content.Context
import com.example.horinoa.weatherhacks.RestAPI.*

interface WeatherHackable{
    fun retroFitCall(city: String?,callback:(WeatherEntity?) -> Unit)
    fun getGeocode(address:String,context: Context,callback:(Position?) -> Unit)
}