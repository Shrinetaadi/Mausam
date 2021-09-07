package com.shrinetaadi.Mausam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
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
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var weatherResponse: MutableLiveData<WeatherResponse>
    private lateinit var aQIResponse: MutableLiveData<AirQuality>
    private lateinit var error: MutableLiveData<Boolean>
    private lateinit var viewModel: HomeViewModel
    val rv3DayViewAdapter = Rv3DayForecastAdapter(arrayListOf())
    val rvHourlyAdapter = RvHourlyAdapter(arrayListOf())
    lateinit var dailymanager: LinearLayoutManager
    lateinit var hourlymanager: LinearLayoutManager

    private var lat: Double? = null
    private var lon: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        lat = Constants.LATITUDE
        lon = Constants.LONGITUDE
        weatherResponse = viewModel.getWeatherResponse(lat!!, lon!!)
        error = viewModel.error

        dailymanager = LinearLayoutManager(this)
        rv3DayForecast.apply {
            layoutManager = dailymanager
            adapter = rv3DayViewAdapter
            isNestedScrollingEnabled = false
        }
        hourlymanager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvHourForecast.apply {
            layoutManager = hourlymanager
            adapter = rvHourlyAdapter
        }

        aQIResponse = viewModel.getAQIQualityResponse()
        initViewModel()

    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        weatherResponse.observe(this, androidx.lifecycle.Observer { weatherResponse ->
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
                    val arg = Bundle()
                    arg.putParcelableArrayList("list", listData)
                    arg.putInt("val", 1)
                    val intent = Intent(this, OtherThingsActivity::class.java)
                    intent.putExtra("arg", arg)
                    startActivity(intent)
                }
                imgAddCity.setOnClickListener {
                    val listData = ArrayList<WeatherResponse.Daily>()
                    val arg = Bundle()
                    arg.putParcelableArrayList("list", listData)
                    arg.putInt("val", 2)
                    val intent = Intent(this, OtherThingsActivity::class.java)
                    intent.putExtra("arg", arg)
                    startActivity(intent)
                }
                imgMenu.setOnClickListener {
                    val popupMenu =
                        PopupMenu(this, it)
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.settings -> {
                                val listData = ArrayList<WeatherResponse.Daily>()
                                val arg = Bundle()
                                arg.putParcelableArrayList("list", listData)
                                arg.putInt("val", 3)
                                val intent = Intent(this, OtherThingsActivity::class.java)
                                intent.putExtra("arg", arg)
                                startActivity(intent)
                                true
                            }
                            else -> false
                        }


                    }
                    popupMenu.inflate(R.menu.menu_home)
                    popupMenu.show()
                }

            }
        })


        aQIResponse.observe(this, androidx.lifecycle.Observer { aQIResponse ->
            aQIResponse?.let {
                txtAQI.text = "AQI " + it.list[0].components.pm10.toInt().toString()
                txtBottomAQI.text = it.list[0].components.pm10.toInt().toString()

            }

        })



        error.observe(this, androidx.lifecycle.Observer
        {
            it?.let {
            }
        })


    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


}