package com.example.newsapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [News.newInstance] factory method to
 * create an instance of this fragment.
 */
class News : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var API_KEY="1fb0dc640b79438286cc3930a21fa480"
    private lateinit var recyclerView: RecyclerView // Declare RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val retrofitObj=RetrofitBuilder()
        val service=retrofitObj.retrofit.create(ApiInterface::class.java)
        // Inside a coroutine (or a ViewModel using coroutines)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make the network request and get the response
                val response = service.getNews(sources = "bbc-news", apiKey = API_KEY)

                // Check if the response is successful
                if (response.isSuccessful) {
                    // Get the news data from the response
                    val newsData = response.body()
                    // Do something with the news data, like updating the UI
                    withContext(Dispatchers.Main) {
                        RecyclerViewAdapter(newsData)
                    }
                }
                else {
                    // Handle the case when the response is not successful
                    withContext(Dispatchers.Main) {
                        //Toast.makeText(,"Error Occurred! Please try again.",Toast.LENGTH_SHORT)
                    }
                }
            }
            catch (e: Exception) {
                // Handle exceptions (e.g., no internet connection, server error)
                withContext(Dispatchers.Main) {
                    // Show error to the user
                    // Example: showError(e.message ?: "Unknown error")
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // Set up the RecyclerView here
        recyclerView = view.findViewById(R.id.recyclerView) // Assume you have a RecyclerView in fragment_news.xml
        recyclerView.layoutManager = LinearLayoutManager(context) // Set layout manager

        fetchNewsData()

        return view
    }

    private fun fetchNewsData() {
        val retrofitObj = RetrofitBuilder()
        val service = retrofitObj.retrofit.create(ApiInterface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make the network request and get the response
                val response = service.getNews(sources = "bbc-news", apiKey = API_KEY)

                // Check if the response is successful
                if (response.isSuccessful) {
                    // Get the news data from the response
                    val newsData = response.body()

                    // Update the UI in the main thread
                    withContext(Dispatchers.Main) {
                        if (newsData != null) {
                            // Set the adapter with the news data
                            recyclerView.adapter = RecyclerViewAdapter(newsData)
                        }
                    }
                } else {
                    // Handle the case when the response is not successful
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error Occurred! Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., no internet connection, server error)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment News.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            News().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}