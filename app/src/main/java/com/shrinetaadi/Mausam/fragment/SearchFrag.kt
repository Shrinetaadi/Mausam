package com.shrinetaadi.Mausam.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.adapter.SearchAdapter
import com.shrinetaadi.Mausam.model.WeatherLocation
import com.shrinetaadi.Mausam.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFrag : Fragment() {
    private lateinit var weatherList: MutableLiveData<List<WeatherLocation.City>>
    private lateinit var queryText: MutableLiveData<String>
    private lateinit var error: MutableLiveData<Boolean>
    lateinit var searchAdapter: SearchAdapter
    private lateinit var searchView: SearchView
    private val disposables = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    companion object {
        val TAG = SearchFrag::class.java.simpleName
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        weatherList = viewModel.getWeatherResponse()
        queryText = viewModel.queryText
        error = viewModel.error
        searchAdapter = SearchAdapter(arrayListOf(), requireFragmentManager())
        searchView = mainSearchView
        createDebounceOperator()

        initViewModel()
        rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }

    }

    private fun createDebounceOperator() {

        val observableQueryText = Observable.create<String> { emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(newText!!)
                    }
                    return false
                }
            })

        }
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        observableQueryText.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onNext(t: String) {
                val str =
                    "onNext: time since last request:" + (System.currentTimeMillis() - timeSinceLastRequest)
                Log.d(TAG, str)
                Log.d(TAG, "onNext: search query:$t")
                timeSinceLastRequest = System.currentTimeMillis()
                sendRequestToserver(t)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete:")
            }

        })

    }

    private fun sendRequestToserver(query: String) {
        queryText.postValue(query.trim())
    }


    private fun initViewModel() {
        weatherList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { weatherList ->
            weatherList?.let {
                val listCity: List<WeatherLocation.City> = it!!
                searchAdapter.updateList(listCity)

            }
        })

        viewModel.queryText.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                viewModel.makeQuery(queryText.value!!)
            }
        })
        error.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            it?.let {
                if (it) {
                    /*    Snackbar.make(, "Network failed", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY") { viewModel.makeQuery(queryText.value!!) }
                            .show()
                    */
                }
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
