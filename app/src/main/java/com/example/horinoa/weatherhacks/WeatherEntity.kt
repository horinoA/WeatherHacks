package com.example.horinoa.weatherhacks

import java.util.*

//LiveDoor 天気Json用データクラス
//@Generated("org.jsonschema2pojo")
data class WeatherEntity(
        val link:String,
        val forecasts: List<Forecast>,
        val publicTime: String,
        val title: String,
        var description: Description,
        val additionalProperties: HashMap<String, Any>
)

data class Description (
    val text: String,
    val publicTime: String,
    val additionalProperties: HashMap<String, Any>
)

data class Forecast (
    val dateLabel: String,
    val telop: String,
    val date: String,
    val temperature :Temperature,
    val additionalProperties : HashMap<String, Any>
)

data class Temperature(val min:min,val max:max,val additionalProperties: HashMap<String, Any>)

data class min(val celsius : String, val fahrenheit : String)
data class max(val celsius : String, val fahrenheit : String)

data class GeoEntity(
        val status:String,
        val results:List<Result>,
        val additionalProperties:HashMap<String, Object>
)

//GoogleGeocoder JSONクラス
data class Location (
    val lat:Double,
    val lng:Double,
    val additionalProperties:HashMap<String, Object>)

data class Geometry(
    val location:Location,
    val additionalProperties:HashMap<String, Object>
)

data class Result(
        val formattedAddress:String,
        val geometry:Geometry,
        val status:String,
        val additionalProperties:HashMap<String, Object>
)

//観測地点用配列保持
data class ObservationPoint_ (
        val city:String,
        val id:String,
        val lat:Double,
        val lng:Double,
        var distance:Double,
        val additionalProperties:HashMap<String, Object>
)

data class ObservationPoint (
    val ObservationPoint : ArrayList<ObservationPoint_>,
    val additionalProperties : HashMap<String, Object>
)
