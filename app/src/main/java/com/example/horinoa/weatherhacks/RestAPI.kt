package com.example.horinoa.weatherhacks

/**
 * Created by horinoA on 2016/02/02.
 */

import android.content.Context
import android.location.Address;
import android.location.Geocoder;
import android.util.Log
import retrofit.*
import java.io.IOException
import java.util.*

class RestAPI:WeatherHackable {

    override fun retroFitCall(city: String?,callback:(WeatherEntity?) -> Unit) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://weather.livedoor.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(WeatherApi::class.java)
        val call = service.getWeather(city)
        call.enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(response: Response<WeatherEntity>, retrofit: Retrofit) {
                callback(response.body())
            }
            override fun onFailure(t: Throwable) {
                callback(null)
            }
        })
    }

    override fun getGeocode(address:String,context:Context,callback:(Position?) -> Unit){
        val gcoder:Geocoder = Geocoder(context, Locale.JAPAN)
        var getAddress:List<Address>
        try{
            getAddress = gcoder.getFromLocationName(address,1)
            if (getAddress != null && getAddress.size > 0){
                Log.d("geo",getAddress.get(0).locality)
                val position = Position(mapOf(
                        "lat" to getAddress.get(0).latitude,
                        "lng" to getAddress.get(0).longitude
                ))
                callback(position)
            }else{
                googleGeocoder(address,{
                    callback(it)
                })
            }
        }catch(e:IOException){
            callback(null)
        }
    }

    fun googleGeocoder(address:String,callback: (Position?) -> Unit){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GeoCoderAPI::class.java)
        val call = service.getGeo(address)
        call.enqueue(object  : Callback<GeoEntity> {
            override fun onResponse(response: Response<GeoEntity>, retrofit: Retrofit?) {
                Log.d("status",response.body().status)
                if(response.body().status.equals("OK")){
                    val position = Position(mapOf(
                        "lat" to response.body().results.get(0).geometry.location.lat,
                        "lng" to response.body().results.get(0).geometry.location.lng
                    ))
                    callback(position)
                }else{
                    callback(null)
                }
            }
            override fun onFailure(t: Throwable) {
                callback(null)
            }
        })
    }

    //緯度経度保持用クラス
    class Position(val map: Map<String, Any?>){
        val lat:Double by map
        val lng:Double by map
    }


}