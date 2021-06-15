package com.example.wuhan

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class showMyRecord : AppCompatActivity() {

    //    // variable
//    lateinit var tv_time: TextView
//    lateinit var tv_number: TextView
//    lateinit var tv_name: TextView
//    lateinit var btn_back: Button
//
//    val PREF_XMLFILE: String = "data"
//    val KEY_NUMBER: String = "number"
//    val KEY_NAME: String = "name"
    lateinit var tv_date: TextView
    lateinit var btn_chooseDate: Button
    lateinit var myRecycler: RecyclerView
    lateinit var btnCheckByStudent: Button
    lateinit var btnCheckByTeacher: Button
    lateinit var editor: SharedPreferences
    private lateinit var data: SignUpRecord
    private val baseUrl = "https://99be04669f0c.ngrok.io"
    private val retrofitManager = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
        GsonConverterFactory.create()
    ).build()
    private val retrofitService = retrofitManager.create(RetrofitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_record)
        editor = getSharedPreferences("data", MODE_PRIVATE)
        myRecycler = findViewById(R.id.myRecycler)
        btnCheckByStudent = findViewById(R.id.checkByStudent)
        btnCheckByTeacher = findViewById(R.id.checkByTeacher)
        tv_date = findViewById(R.id.tv_date)
        btn_chooseDate = findViewById(R.id.btn_chooseDate)

        btnCheckByStudent.setOnClickListener { getStudent() }
        btnCheckByTeacher.setOnClickListener { getTeacher() }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        btn_chooseDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    tv_date.setText(
                        "" + mYear + "-" + String.format(
                            "%02d",
                            mMonth + 1
                        ) + "-" + String.format("%02d", mDay)
                    )
                },
                year,
                month,
                day
            )
            dpd.show()
        }

//        // find id
//        tv_time = findViewById(R.id.tv_time)
//        tv_number = findViewById(R.id.tv_number)
//        tv_name = findViewById(R.id.tv_name)
//        btn_back = findViewById(R.id.btn_back)
//
//        // showData in data.xml
//        val pref = getSharedPreferences(PREF_XMLFILE, MODE_PRIVATE)
//        val pref_number = pref.getInt(KEY_NUMBER, 1)
//        tv_number.text = "" + pref_number
//        val pref_name = pref.getString(KEY_NAME, "1")
//        tv_name.text = pref_name
//
//        // showTime
//        tv_time.text = showTime()
//
//        // when back is clicked
//        btn_back.setOnClickListener { back() }
    }

//    private fun back() {
//        onBackPressed()
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

    //    // 月要 + 1, 因為是從0~11
//    private fun showTime(): String {
//        val times = Calendar.getInstance()
//        val msg = StringBuffer()
//        msg.append("你的簽到時間為 : ")
//        msg.append(times.get(Calendar.YEAR).toString() + " / ")
//        msg.append((times.get(Calendar.MONTH) + 1 ).toString() + " / ")
//        msg.append(times.get(Calendar.DAY_OF_MONTH).toString() + " / ")
//        msg.append(times.get(Calendar.HOUR_OF_DAY).toString() + ":")
//        msg.append(times.get(Calendar.MINUTE).toString() + ":")
//        msg.append(times.get(Calendar.SECOND).toString() + "\n")
//        return msg.toString()
//    }
    private fun getStudent() {
        Toast.makeText(this@showMyRecord, "學生僅能查看今日的簽到記錄", Toast.LENGTH_SHORT)
            .show()
        var name: String? = editor.getString("name", "無")
        var phone: String? = editor.getString("phone", "無")
        var number: String? = editor.getString("number", "無")
        var location: String? = editor.getString("location", "無")
        var time: String? = editor.getString("time", "無")

        if (name == null) {
            name = "無"
        }
        if (phone == null) {
            phone = "無"
        }
        if (number == null) {
            number = "無"
        }
        if (location == null) {
            location = "無"
        }
        if (time == null) {
            time = "無"
        }

        data = SignUpRecord(name, number, phone, location, time)
        var record: MutableList<SignUpRecord> = mutableListOf()
        record.add(data)
        var linearLayoutManager: LinearLayoutManager
        linearLayoutManager = LinearLayoutManager(this)
        myRecycler.layoutManager = linearLayoutManager
        myRecycler.adapter = checkAdapter(this@showMyRecord, record)


    }

    private fun getTeacher() {
        if (tv_date.getText().toString().equals("請選擇日期")) {
            Toast.makeText(this@showMyRecord, "請先選擇欲查詢日期", Toast.LENGTH_SHORT)
                .show()
        } else {
            retrofitService.checkByTeacher(tv_date.getText().toString())
                .enqueue(object : Callback<List<SignUpRecord>> {
                    override fun onResponse(
                        call: Call<List<SignUpRecord>>,
                        response: Response<List<SignUpRecord>>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@showMyRecord,
                                "老師可查看當日所有學生的簽到情形",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            var linearLayoutManager: LinearLayoutManager
                            linearLayoutManager = LinearLayoutManager(this@showMyRecord)
                            myRecycler.layoutManager = linearLayoutManager
                            myRecycler.adapter =
                                response.body()?.let { checkAdapter(this@showMyRecord, it) }
                        } else {
                            Toast.makeText(this@showMyRecord, "伺服器錯誤，請稍後再試", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<List<SignUpRecord>>, t: Throwable) {
                        Toast.makeText(this@showMyRecord, "連線錯誤，請稍後再試", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }

    }
}