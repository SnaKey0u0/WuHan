package com.example.wuhan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class showMyRecord : AppCompatActivity() {

    // variable
    lateinit var tv_time: TextView
    lateinit var tv_number: TextView
    lateinit var tv_name: TextView
    lateinit var btn_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_record)

        // find id
        tv_time = findViewById(R.id.tv_time)
        tv_number = findViewById(R.id.tv_number)
        tv_name = findViewById(R.id.tv_name)
        btn_back = findViewById(R.id.btn_back)

        // showData in data.xml
        val pref = getSharedPreferences(MainActivity.PREF_XMLFILE, MODE_PRIVATE)
        val pref_number = pref.getInt(MainActivity.KEY_NUMBER, 1)
        tv_number.text = "" + pref_number
        val pref_name = pref.getString(MainActivity.KEY_NAME, "1")
        tv_name.text = pref_name

        // showTime
        tv_time.text = showTime()

        // when back is clicked
        btn_back.setOnClickListener { back() }
    }

    private fun back() {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showTime(): String {
        val times = Calendar.getInstance()
        val msg = StringBuffer()
        msg.append(times.get(Calendar.YEAR).toString() + " / ")
        msg.append(times.get(Calendar.MONTH).toString() + " / ")
        msg.append(times.get(Calendar.DAY_OF_MONTH).toString() + " / ")
        msg.append(times.get(Calendar.HOUR_OF_DAY).toString() + ":")
        msg.append(times.get(Calendar.MINUTE).toString() + ":")
        msg.append(times.get(Calendar.SECOND).toString() + "\n")
        return msg.toString()
    }
}