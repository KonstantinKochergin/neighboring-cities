package com.example.neighboringcities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    lateinit var cities: Cities
    lateinit var citiesSpinner: Spinner
    lateinit var maxDistanceInp: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.citiesSpinner = findViewById(R.id.citiesSpinner)
        this.maxDistanceInp = findViewById(R.id.distanceEdt)

        val citiesStream = resources.openRawResource(R.raw.cities)
        val gson = Gson()
        this.cities = gson.fromJson(InputStreamReader(citiesStream), Cities::class.java)

        val citiesNames = this.cities.cities.map { city -> city.name }
        val adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, citiesNames)
        this.citiesSpinner.adapter = adapter
    }

    fun goToCitiesListButtonClick(v: View) {
        val selectedCityName = this.citiesSpinner.selectedItem.toString()
        val maxDistance = this.maxDistanceInp.text.toString().toInt()
        val intent = Intent(this, NeighboringCitiesActivity::class.java)
        intent.putExtra("cityName", selectedCityName)
        intent.putExtra("maxDistance", maxDistance)
        startActivity(intent)
    }
}