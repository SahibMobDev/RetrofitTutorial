package com.example.retrofittutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittutorial.adapter.ProductAdapter
import com.example.retrofittutorial.databinding.ActivityMainBinding
import com.example.retrofittutorial.retrofit.AuthRequest
import com.example.retrofittutorial.retrofit.MainApi
import com.example.retrofittutorial.retrofit.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.rcView.adapter = adapter

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mainApi = retrofit.create(MainApi::class.java)
        
        var user: User? = null

        CoroutineScope(Dispatchers.IO).launch {
            user = mainApi.auth(
                AuthRequest(
                    "kminchelle",
                    "0lelplR"
                )
            )

            supportActionBar?.title = user?.firstName
        }


        binding.sv.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val list = query?.let { mainApi.getAuthProductsByName(user?.token ?: "",it) }
                    runOnUiThread {
                        binding.apply {
                            adapter.submitList(list?.products)
                        }
                    }

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })


    }
}