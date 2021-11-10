package com.example.neighboringcities

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson
import java.io.InputStreamReader

class NeighboringCitiesActivity : AppCompatActivity() {
    lateinit var cities: Cities
    lateinit var citiesList: ListView
    lateinit var targetCity: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neighboring_cities)

        citiesList = findViewById(R.id.neighboringCitiesList)

        val citiesStream = resources.openRawResource(R.raw.cities)
        val gson = Gson()
        this.cities = gson.fromJson(InputStreamReader(citiesStream), Cities::class.java)

        val targetCityName = intent.getStringExtra("cityName")
        val maxDistance = intent.getIntExtra("maxDistance", 0)
        this.targetCity = this.cities.cities.filter { city -> city.name.equals(targetCityName) }[0]
        val neighboringCities = this.cities.cities.filter { city -> (getDistanceInKm(this.targetCity, city) <= maxDistance) && !city.name.equals(targetCityName)}
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, neighboringCities.map { city -> city.name })
        citiesList.adapter = adapter
    }

    private fun getDistanceInKm(city1: City, city2: City) : Double {
        val city1Point = Location("city1")
        city1Point.latitude = city1.coord.lat
        city1Point.longitude = city1.coord.lon
        val city2Point = Location("city2")
        city2Point.latitude = city2.coord.lat
        city2Point.longitude = city2.coord.lon
        return city1Point.distanceTo(city2Point) * 0.001
    }

    fun backToMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}