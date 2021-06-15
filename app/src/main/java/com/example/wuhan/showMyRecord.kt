package com.example.wuhan

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    lateinit var myRecycler: RecyclerView
    lateinit var btnCheckByStudent: Button
    lateinit var btnCheckByTeacher: Button
    lateinit var editor: SharedPreferences
    private lateinit var data: SignUpRecord
    private val baseUrl = "https://a540f982cba6.ngrok.io/"
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

        btnCheckByStudent.setOnClickListener { getStudent() }
        btnCheckByTeacher.setOnClickListener { getTeacher() }
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
        var name: String? = editor.getString("name", "無")
        var phone: String? = editor.getString("phone", "無")
        var number: String? = editor.getString("number", "無")
        var location: String? = editor.getString("location", "無")
        var time: String? = editor.getString("time", "無")

        if (name == null) {
            name = ""
        }
        if (phone == null) {
            phone = ""
        }
        if (number == null) {
            number = ""
        }
        if (location == null) {
            location = ""
        }
        if (time == null) {
            time = ""
        }

        data = SignUpRecord("name", "number", "phone", "location", "time")
        var record: MutableList<SignUpRecord> = mutableListOf()
        record.add(data)
        record.add(data)
        var linearLayoutManager: LinearLayoutManager
        linearLayoutManager = LinearLayoutManager(this)
        myRecycler.layoutManager = linearLayoutManager
        myRecycler.adapter = checkAdapter(this@showMyRecord, record)


    }

    private fun getTeacher() {
//        retrofitService.checkByTeacher(data).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    //TODO:recyclerView
//                } else {
//                    Toast.makeText(this@showMyRecord, response.body(), Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(this@showMyRecord, "連線失敗，請稍後再試", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
    }
}