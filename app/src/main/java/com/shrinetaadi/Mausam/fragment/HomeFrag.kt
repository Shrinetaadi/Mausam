package com.shrinetaadi.Mausam.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.adapter.Rv3DayForecastAdapter
import com.shrinetaadi.Mausam.adapter.RvHourlyAdapter
import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.WeatherResponse
import com.shrinetaadi.Mausam.util.Constants
import com.shrinetaadi.Mausam.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFrag : Fragment(R.layout.fragment_home) {
    private lateinit var weatherResponse: MutableLiveData<WeatherResponse>
    private lateinit var aQIResponse: MutableLiveData<AirQuality>
    private lateinit var lat: MutableLiveData<Double>
    private lateinit var lon: MutableLiveData<Double>
    private lateinit var error: MutableLiveData<Boolean>
    private lateinit var viewModel: HomeViewModel
    val rv3DayViewAdapter = Rv3DayForecastAdapter(arrayListOf())
    val rvHourlyAdapter = RvHourlyAdapter(arrayListOf())
    lateinit var dailymanager: LinearLayoutManager
    lateinit var hourlymanager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        weatherResponse = viewModel.getWeatherResponse()
        error = viewModel.error
        lat = viewModel.lat
        lon = viewModel.lon
        lat.value = Constants.LATITUDE
        lon.value = Constants.LONGITUDE


        dailymanager = LinearLayoutManager(requireContext())
        rv3DayForecast.apply {
            layoutManager = dailymanager
            adapter = rv3DayViewAdapter
            isNestedScrollingEnabled = false
        }
        hourlymanager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvHourForecast.apply {
            layoutManager = hourlymanager
            adapter = rvHourlyAdapter
        }

        aQIResponse = viewModel.getAQIQualityResponse()
        initViewModel()

    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {

        weatherResponse.observe(viewLifecycleOwner, Observer { weatherResponse ->
            weatherResponse?.let {

                txtTemprature.text = (it.current.temp).roundToInt().toString()
                txtSunriseTime.text = SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                ).format(Date(it.current.sunrise * 1000))
                txtSunsetTime.text = SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                ).format(Date(it.current.sunset * 1000))
                txtRealTemp.text = it.current.feels_like.roundToInt().toString() + "°C"
                txtHumidity.text = it.current.humidity.toString() + "%"
                txtPressure.text = it.current.pressure.toString() + "hPa"
                txtUVIndex.text = it.current.uvi.toString()
                txtWindSpeed.text = it.current.wind_speed.toString() + "m/sec"
                txtRainChance.text = ((it.daily[0].pop) * 100).roundToInt().toString() + "%"
                val dailyData: List<WeatherResponse.Daily> = it.daily

                rv3DayViewAdapter.updateData(dailyData)
                val hourlyData: List<WeatherResponse.Hourly> = it.hourly
                rvHourlyAdapter.updateData(hourlyData)
                txtWeatherStatusNow.text = it.current.weather[0].description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }

                btn5DayForecast.setOnClickListener {
                    val listData = dailyData as ArrayList<WeatherResponse.Daily>
                    replaceFragment(DailyFragment(), listData)
                }


            }
            aQIResponse.observe(viewLifecycleOwner, Observer { aQIResponse ->
                aQIResponse?.let {
                    txtAQI.text = "AQI " + it.list[0].components.pm10.toInt().toString()
                    txtBottomAQI.text = it.list[0].components.pm10.toInt().toString()

                }

            })
        })



        error.observe(viewLifecycleOwner, Observer {
            it?.let {
            }
        })


    }

    private fun replaceFragment(fragment: Fragment, listData: ArrayList<WeatherResponse.Daily>) {
        val arg = Bundle()
        arg.putParcelableArrayList("listData", listData)

        fragment.arguments = arg
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left
        )

        fragmentTransaction.replace(R.id.frameLayout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()

    }

}