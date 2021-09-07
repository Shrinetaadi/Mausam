package com.shrinetaadi.Mausam.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.activity.OtherThingsActivity
import com.shrinetaadi.Mausam.adapter.RvAddCityAdapter
import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.CityList
import com.shrinetaadi.Mausam.model.WeatherList
import com.shrinetaadi.Mausam.model.WeatherResponse
import com.shrinetaadi.Mausam.room.WeatherDatabase
import com.shrinetaadi.Mausam.viewmodel.AddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_city.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class AddCity : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var country: String? = null
    private var lat: Double? = null
    private var lon: Double? = null
    private var listCity: List<WeatherList> = listOf()
    private var entered = false

    //viewmodel
    private lateinit var weatherResponse: MutableLiveData<CityList>
    private lateinit var idNew: MutableLiveData<Int>
    private lateinit var error: MutableLiveData<Boolean>
    private lateinit var viewModel: AddViewModel
    private lateinit var nameNew: MutableLiveData<String>
    private lateinit var countryNew: MutableLiveData<String>
    private lateinit var latNew: MutableLiveData<Double>
    private lateinit var lonNew: MutableLiveData<Double>
    private lateinit var liveDataList: MutableLiveData<WeatherResponse>
    private lateinit var liveAQIData: MutableLiveData<AirQuality>

    //
    val rvAddCityAdapter = RvAddCityAdapter(arrayListOf())
    lateinit var addCitymanager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            country = it.getString("country")
            lat = it.getDouble("lat")
            lon = it.getDouble("lon")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, country: String, lat: Double, lon: Double) =
            AddCity().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("country", country)
                    putDouble("lat", lat)
                    putDouble("lon", lon)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        weatherResponse = viewModel.getWeatherResponseCity()
        idNew = viewModel.idNew
        error = viewModel.error
        nameNew = MutableLiveData()
        countryNew = MutableLiveData()
        latNew = MutableLiveData()
        lonNew = MutableLiveData()
        liveAQIData = viewModel.liveAQIData
        liveDataList = viewModel.liveDataList

        runBlocking {
            launch {
                context?.let {
                    listCity = WeatherDatabase.getDatabase(it).weatherDao().allEntries()
                }
            }
        }
        llSearch.setOnClickListener {
            replaceFragment(SearchFrag())
        }
        imgBackAddCity.setOnClickListener {
            requireActivity().onBackPressed()
        }
//        Log.i(OtherThingsActivity::class.java.simpleName, listCity[0].name.toString())
        addCitymanager = LinearLayoutManager(requireContext())
        rvAddCity.apply {
            layoutManager = addCitymanager
            adapter = rvAddCityAdapter
        }
        rvAddCityAdapter.clearData()


        if (listCity.size != 0) {
            for (i in 0 until listCity.size) {
                entered = name?.lowercase().equals(listCity[i].name?.lowercase())
            }

            if (!entered) {
                if (name != null) {
                    Log.i(OtherThingsActivity::class.java.simpleName, "E1")
                    runBlocking {
                        launch {
                            context?.let {
                                val new = WeatherList()
                                new.name = name
                                new.country = country
                                new.lat = lat
                                new.lon = lon
                                WeatherDatabase.getDatabase(it).weatherDao().insertEntry(new)
                                listCity = WeatherDatabase.getDatabase(it).weatherDao().allEntries()
                                entered = false
                                Log.i(OtherThingsActivity::class.java.simpleName, "E2")
                            }
                        }
                    }

                }


            }
        } else {
            if (name != null) {
                Log.i(OtherThingsActivity::class.java.simpleName, "E1")
                runBlocking {
                    launch {
                        context?.let {
                            val new = WeatherList()
                            new.name = name
                            new.country = country
                            new.lat = lat
                            new.lon = lon
                            WeatherDatabase.getDatabase(it).weatherDao().insertEntry(new)
                            listCity = WeatherDatabase.getDatabase(it).weatherDao().allEntries()
                            entered = false
                            Log.i(OtherThingsActivity::class.java.simpleName, "E2")
                        }
                    }
                }

            }


            Log.i(OtherThingsActivity::class.java.simpleName, "here")

        }

        Log.i(OtherThingsActivity::class.java.simpleName, listCity.size.toString())


        if (listCity.size != 0) {
            for (j in 0..listCity.size - 1) {
                Log.i(
                    OtherThingsActivity::class.java.simpleName,
                    j.toString() + listCity[j].name.toString()
                )
                latNew.postValue(listCity[j].lat)
                lonNew.postValue(listCity[j].lon)
                nameNew.postValue(listCity[j].name)
                countryNew.postValue(listCity[j].country)
                Log.i(OtherThingsActivity::class.java.simpleName, listCity[j].name.toString())
                idNew.postValue(listCity[j].id)

            }

            initViewModel()


        }


    }

    private fun initViewModel() {
        Log.i(OtherThingsActivity::class.java.simpleName, "E4")
        viewModel.idNew.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.let {
                viewModel.getResult(
                    latNew.value!!,
                    lonNew.value!!,
                    idNew.value!!,
                    nameNew.value!!,
                    countryNew.value!!
                )
            }

            weatherResponse.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer { weatherResponse ->

                    weatherResponse?.let {
                        Log.i(OtherThingsActivity::class.java.simpleName, "E5" + it.name)

                        rvAddCityAdapter.updateData(it)

                    }


                })

        })







        error.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            it?.let {
            }
        })
        //

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left
        )

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.simpleName).commit()
    }


}