package com.example.wuhan

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val baseUrl = "http://domainName/api/"
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
        btnCheck = findViewById(R.id.btnCheck)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            postData()
        }
    }

    private fun postData() {
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

}