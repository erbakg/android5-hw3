package com.example.android5_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android5_3.data.ImageModel
import com.example.android5_3.data.ImagesModel
import com.example.android5_3.data.RetrofitClient
import com.example.android5_3.databinding.ActivityMainBinding
import com.example.android5_3.recyclerview.ImagesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    var adapter = ImagesAdapter()
    private var totalAvailablePages = 1
    private lateinit var imagesList: ArrayList<ImageModel>
    private var page = 1
    private var searchedWord = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imagesList = ArrayList()
        binding.rvImages.adapter = adapter
        initClickers()
        initListeners()
    }

    private fun initListeners() {
        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvImages.canScrollVertically(1)) {
                    if (page <= totalAvailablePages) {
                        page += 1
                        requestImage()
                    }
                }
            }
        })
    }

    private fun initClickers() {
        binding.btnSearch.setOnClickListener {
            if (searchedWord !== binding.etSearch.text.toString()) {
                imagesList.clear()
                searchedWord = binding.etSearch.text.toString()
                requestImage()
            } else {
                requestImage()
            }

        }
        binding.btnNext.setOnClickListener {
            if (searchedWord === binding.etSearch.text.toString()) {
                ++page
                requestImage()
            } else {
                Toast.makeText(
                    baseContext,
                    "You changed word, please click SEARCH button",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun requestImage() {
        val call = RetrofitClient.apiService.getImages(
            query = binding.etSearch.text.toString(),
            page = page
        )
        call.enqueue(object : Callback<ImagesModel> {
            override fun onResponse(call: Call<ImagesModel>, response: Response<ImagesModel>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val oldCount = imagesList.size
                    totalAvailablePages = data!!.totalHits
                    imagesList.addAll(data!!.hits)
                    adapter.updateList(imagesList, oldCount, imagesList.size)
                    adapter.notifyDataSetChanged()

                } else {
                    Log.d("haha", "onResponse: " + response.toString())
                    Toast.makeText(baseContext, "Your request error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ImagesModel>, t: Throwable) {
                Log.d("haha", "onFailure: " + t.message)
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}