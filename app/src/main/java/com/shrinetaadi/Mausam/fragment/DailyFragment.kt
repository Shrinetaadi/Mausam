package com.shrinetaadi.Mausam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.adapter.RvDailyAdapter
import com.shrinetaadi.Mausam.model.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_daily.*


class DailyFragment : Fragment(R.layout.fragment_daily) {

    lateinit var dailyManager: LinearLayoutManager

    private lateinit var listData: ArrayList<WeatherResponse.Daily>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listData =
            arguments?.getParcelableArrayList<WeatherResponse.Daily>("listData") as ArrayList<WeatherResponse.Daily>
        dailyManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val rvDailyAdapter = RvDailyAdapter(listData)
        rvDailyForecast.apply {
            layoutManager = dailyManager
            adapter = rvDailyAdapter
        }
        imgBackDaily.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }


}