package com.shrinetaadi.Mausam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.fragment.AddCity
import com.shrinetaadi.Mausam.model.WeatherLocation
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(
    val array: ArrayList<WeatherLocation.City>,
    val f_manager: FragmentManager
) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun updateList(list: List<WeatherLocation.City>) {
        array.clear()
        array.addAll(list)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = array[position]
        holder.itemView.txtRes.text =
            data.name?.capitalize() + " , " + data.country

        holder.itemView.txtRes.setOnClickListener {

            val frag = AddCity.newInstance(
                data.name.toString(), data.country.toString(),
                data.lat!!, data.lon!!
            )
            f_manager.beginTransaction().replace(R.id.frameLayout, frag).commit()
        }

    }

    override fun getItemCount(): Int {
        return array.size
    }


}
