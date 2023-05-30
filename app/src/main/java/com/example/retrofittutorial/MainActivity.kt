package com.example.retrofittutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.retrofittutorial.retrofit.ProductApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)
        val b = findViewById<Button>(R.id.button)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productApi = retrofit.create(ProductApi::class.java)

        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(3)
                runOnUiThread {
                    tv.text = product.title
                }
            }
        }
    }
}