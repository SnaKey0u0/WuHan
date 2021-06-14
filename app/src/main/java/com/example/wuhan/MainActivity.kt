package com.example.wuhan

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {

    // variable
    lateinit var tv_loc: TextView
    lateinit var et_number: EditText
    lateinit var et_name: EditText
    lateinit var btn_check: Button
    lateinit var btn_look: Button
    lateinit var locmgr: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find id
        tv_loc = findViewById(R.id.tv_loc)
        et_number = findViewById(R.id.et_number)
        et_name = findViewById(R.id.et_number)
        btn_check = findViewById(R.id.button1)
        btn_look = findViewById(R.id.button2)

        // about location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            initLoc()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            initLoc()
        }
    }

    private fun initLoc() {
        locmgr = getSystemService(LOCATION_SERVICE) as LocationManager
        var loc: Location? = null
        try {
            loc = locmgr.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (loc == null) {
                loc = locmgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        } catch (e: SecurityException) {
        }

        if (loc != null) {
            tv_loc.text = showLocation(loc)
        } else {
            tv_loc.text = "cannot get location!"
        }

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        val provider: String? = locmgr.getBestProvider(criteria, true)
        try {
            if (provider != null) {
                locmgr.requestLocationUpdates(provider, 1000, 1f, this)
            } else {
                locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)
            }
        } catch (e: SecurityException) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locmgr.removeUpdates(this)
    }

    override fun onLocationChanged(loc: Location) {
        tv_loc.text = showLocation(loc)
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onProviderDisabled(provider: String) {
    }

    private fun showLocation(loc: Location): String {
        val msg = StringBuffer()
        msg.append("您目前位置為 => \n")
        msg.append("定位提供者(Provider): ")
        msg.append(loc.provider)
        msg.append("\n緯度(Latitude):  ")
        msg.append(loc.latitude.toString())
        msg.append("\n經度(Longitude): ")
        msg.append(loc.longitude.toString())
        msg.append("\n高度(Altitude):  ")
        msg.append(loc.altitude.toString())
        msg.append("\n")
        return msg.toString()
    }

}