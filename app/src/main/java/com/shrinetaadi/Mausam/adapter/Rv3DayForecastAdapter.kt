package com.shrinetaadi.Mausam.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.model.WeatherResponse
import kotlinx.android.synthetic.main.item_rv_3days.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Rv3DayForecastAdapter(private var listDailyData: ArrayList<WeatherResponse.Daily>) :
    RecyclerView.Adapter<Rv3DayForecastAdapter.Day3ViewHolder>() {

    fun updateData(newData: List<WeatherResponse.Daily>) {
        listDailyData.clear()
        listDailyData.add(newData[0])
        listDailyData.add(newData[1])
        listDailyData.add(newData[2])
        notifyDataSetChanged()
    }

    class Day3ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Day3ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_3days, parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Day3ViewHolder, position: Int) {
        val daily = listDailyData[position]
        when (position) {
            0 -> holder.itemView.txtWeatherStatus3Day.text = "Today"
            1 -> holder.itemView.txtWeatherStatus3Day.text = "Tomorrow"
            2 -> holder.itemView.txtWeatherStatus3Day.text = SimpleDateFormat(
                "EEEE",
                Locale.ENGLISH
            ).format(Date(daily.dt * 1000))
        }
        holder.itemView.txtTemp3Day.text =
            (daily.temp.max.roundToInt().toString()) + "° / " + (daily.temp.min.roundToInt()
                .toString()) + "°"
        holder.itemView.txtWeatherStatus3Day.append("-" + daily.weather[0].main)
        Glide.with(holder.itemView)
            .load("https://openweathermap.org/img/wn/${daily.weather[0].icon}@2x.png")
            .into(holder.itemView.imgWeatherIcon3DAy)

    }

    override fun getItemCount(): Int {
        return listDailyData.size
    }
}