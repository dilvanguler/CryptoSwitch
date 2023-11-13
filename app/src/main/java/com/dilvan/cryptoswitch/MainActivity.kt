package com.dilvan.cryptoswitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dilvan.cryptoswitch.endpoint.ApiClient
import com.dilvan.cryptoswitch.endpoint.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val postId = 1 // Replace with the desired post ID
            val call = ApiClient.apiService.getPostById(postId)

            call.enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        val post = response.body()
                        // Handle the retrieved post data
                    } else {
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}