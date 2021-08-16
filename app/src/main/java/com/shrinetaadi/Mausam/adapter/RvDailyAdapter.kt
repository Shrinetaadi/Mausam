package com.shrinetaadi.Mausam.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.model.WeatherResponse
import kotlinx.android.synthetic.main.item_daily.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class RvDailyAdapter(private var listDailyData: ArrayList<WeatherResponse.Daily>) :
    RecyclerView.Adapter<RvDailyAdapter.HourlyViewHolder>() {
    var row_index = 0

    class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HourlyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val data = listDailyData[position]
        when (position) {
            0 -> holder.itemView.txtDailyDay.text = "Today"
            1 -> holder.itemView.txtDailyDay.text = "Tomorrow"
            else -> holder.itemView.txtDailyDay.text = SimpleDateFormat(
                "EEEE",
                Locale.ENGLISH
            ).format(Date(data.dt * 1000))
        }

        holder.itemView.txtDailyDate.text =
            SimpleDateFormat("M/dd", Locale.US).format(Date(data.dt * 1000))

        Glide.with(holder.itemView)
            .load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
            .into(holder.itemView.imgMorningWeather)

        holder.itemView.txtMaxTemp.text = data.temp.max.roundToInt().toString() + "°C"

        holder.itemView.txtMinTemp.text = data.temp.min.roundToInt().toString() + "°C"

        Glide.with(holder.itemView)
            .load("https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png")
            .into(holder.itemView.imgEveningWeather)

        holder.itemView.txtWindSpeedDaily.text = data.wind_speed.toString() + "m/sec"

        holder.itemView.setOnClickListener {
            row_index = position
            notifyDataSetChanged()
        }
        if (row_index == position) {
            holder.itemView.llDaily.background =
                holder.itemView.context.getDrawable(R.drawable.bg_rounded_selected)
        } else {
            holder.itemView.llDaily.background =
                holder.itemView.context.getDrawable(R.drawable.bg_rounded_unselected)
        }


    }

    override fun getItemCount() = 7
}