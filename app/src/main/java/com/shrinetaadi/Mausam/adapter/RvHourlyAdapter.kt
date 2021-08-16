package com.shrinetaadi.Mausam.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.model.WeatherResponse
import kotlinx.android.synthetic.main.item_rv_hour.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class RvHourlyAdapter(var listHourlyData: ArrayList<WeatherResponse.Hourly>) :
    RecyclerView.Adapter<RvHourlyAdapter.HourlyViewHolder>() {
    var currentDate:Long =1

    @SuppressLint("SetTextI18n")
    class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

    fun updateData(newData: List<WeatherResponse.Hourly>) {
        listHourlyData.clear()
        listHourlyData.addAll(newData)
        currentDate = listHourlyData[0].dt
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HourlyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_hour, parent, false)
    )

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val data = listHourlyData[position]
    if (SimpleDateFormat("M/dd", Locale.ENGLISH).format(Date(currentDate*1000))==SimpleDateFormat("M/dd", Locale.ENGLISH).format(Date(data.dt*1000)))
    {holder.itemView.txtHourTime.text = SimpleDateFormat(
            "HH:mm",
            Locale.ENGLISH
        ).format(Date(data.dt * 1000))
    }else{
        holder.itemView.txtHourTime.text = SimpleDateFormat("M/dd", Locale.ENGLISH).format(Date(data.dt*1000))
        currentDate=data.dt
    }



        holder.itemView.txtHourTemp.text = data.temp.roundToInt().toString() + "°C"
        holder.itemView.txtHourWindSpeed.text = data.wind_speed.toString() + "m/sec"
        Glide.with(holder.itemView)
            .load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
            .into(holder.itemView.imgHourWheaterIcon)

    }

    override fun getItemCount() = listHourlyData.size
}