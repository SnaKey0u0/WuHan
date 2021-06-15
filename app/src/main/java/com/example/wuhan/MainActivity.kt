package com.example.wuhan

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {

    // variable
    lateinit var tv_loc: TextView
    lateinit var et_number: EditText
    lateinit var et_name: EditText
    lateinit var btn_check: Button
    lateinit var btn_look: Button
    lateinit var locmgr: LocationManager

    var PREF_XMLFILE: String = "data"
    var KEY_NUMBER: String = "number"
    var KEY_NAME: String = "name"
    var Latitude = 25.0
    var Longitude = 121.0
    var getLatitude = 0.0
    var getLongitude = 0.0
    var hour: Int = 22
    var min: Int = 59

    private val baseUrl = "https://a540f982cba6.ngrok.io/"
    private val retrofitManager = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        GsonConverterFactory.create()
    ).build()
    private val retrofitService = retrofitManager.create(RetrofitService::class.java)
    private lateinit var btnCheck: Button
    private lateinit var btnSignUp: Button
    private lateinit var data: SignUpRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find id
        tv_loc = findViewById(R.id.tv_loc)
        et_number = findViewById(R.id.et_number)
        et_name = findViewById(R.id.et_name)
        btn_check = findViewById(R.id.button1)
        btn_look = findViewById(R.id.button2)

        // about location
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        } else {
            initLoc()
        }

        // read sharedPreferences
        val pref = getSharedPreferences(PREF_XMLFILE, MODE_PRIVATE)
        val pref_number = pref.getInt(KEY_NUMBER, 1)
        et_number?.setText("" + pref_number)
        val pref_name = pref.getString(KEY_NAME, "1")
        et_name?.setText(pref_name)

        // when btn_check is clicked
        btn_check.setOnClickListener { check() }

        // when btn_look is clicked
        btn_look.setOnClickListener { look() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
        msg.append("您目前位置為 : \n")
        msg.append("定位提供者(provider): ")
        msg.append(loc.provider)
        msg.append("\n緯度(latitude):  ")
        msg.append(loc.latitude.toString())
        getLatitude = loc.latitude
        msg.append("\n經度(longitude): ")
        msg.append(loc.longitude.toString())
        getLongitude = loc.longitude
        msg.append("\n高度(altitude):  ")
        msg.append(loc.altitude.toString())
        msg.append("\n簽到經緯度為 " + Longitude.toString() + " / " + Latitude.toString())
        msg.append("\n簽到dealine為今日的 " + hour.toString() + " : " + min.toString())
        msg.append("\n")
        return msg.toString()
    }

    private fun check() {
        val times = Calendar.getInstance()

        if (et_number.getText().toString() == "" || et_name.getText().toString() == "") {
            Toast.makeText(this, "資料不得為空", Toast.LENGTH_LONG).show()
        }
        // 距離超出GPS原點2公尺
        else if (Math.sqrt(
                Math.pow(
                    getLatitude - Latitude,
                    2.0
                ) + Math.pow(getLongitude - Longitude, 2.0)
            ) > 2
        ) {
            Toast.makeText(this, "簽到失敗, 因為你不在附近", Toast.LENGTH_LONG).show()
        }
        // 時間超出預設deadline
        else if ((Integer.parseInt(times.get(Calendar.HOUR_OF_DAY).toString()) > hour) ||
            ((Integer.parseInt(times.get(Calendar.HOUR_OF_DAY).toString()) == hour &&
                    Integer.parseInt(times.get(Calendar.MINUTE).toString()) > min))
        ) {
            Toast.makeText(this, "簽到失敗, 因為你遲到了", Toast.LENGTH_LONG).show()
        } else {
            // 儲存簽到資料
            saveData()

            //跳轉到顯示紀錄
            val intent = Intent()
            intent.setClass(this, showMyRecord::class.java)
            startActivity(intent)
            finish()
        }
    }

    // saveData
    private fun saveData() {
        // saveData in data.xml
        val pref: SharedPreferences = getSharedPreferences(PREF_XMLFILE, MODE_PRIVATE)

        // use editor to put data
        val editor: SharedPreferences.Editor = pref.edit()
        val number: Int = Integer.parseInt(et_number.getText().toString())
        val name: String = et_name.getText().toString()
        editor.putInt(KEY_NUMBER, number)
            .putString(KEY_NAME, name)
            .apply()

        // toast message
        Toast.makeText(this, "簽到成功", Toast.LENGTH_LONG).show()
    }

    // ready to write by Snakey
    private fun look() {
        data = SignUpRecord("1", "1", "1", "1", "1")
        retrofitService.signUp(data).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    //TODO:recyclerView
                } else {
                    Toast.makeText(this@MainActivity, response.body(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity, "連線失敗，請稍後再試", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private fun postData() {
    }
}