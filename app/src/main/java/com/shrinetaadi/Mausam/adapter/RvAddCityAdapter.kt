package com.shrinetaadi.Mausam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.activity.OtherThingsActivity
import com.shrinetaadi.Mausam.model.CityList
import kotlinx.android.synthetic.main.item_add_city.view.*
import java.util.*
import kotlin.math.roundToInt

class RvAddCityAdapter(var list: ArrayList<CityList>) :
    RecyclerView.Adapter<RvAddCityAdapter.MyViewHolder>() {
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

    fun updateData(data: CityList) {
        list.add(data)
        notifyDataSetChanged()
    }
    fun clearData(){
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_add_city, parent, false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(OtherThingsActivity::class.java.simpleName, "E5")
        val data = list[position]
        holder.itemView.txtCityName.text =
            data.name?.capitalize(Locale.ENGLISH) + "," + data.country
        holder.itemView.txtAQICity.text = "AQI " + data.aqi?.toInt().toString()
        holder.itemView.txtTemp.text = data.temp?.roundToInt().toString() + "°"
        holder.itemView.txtMaxMinCity.text =
            data.max?.roundToInt().toString() + "°" + "/" + data.min?.roundToInt().toString() + "°"
    }

    override fun getItemCount() = list.size

}
