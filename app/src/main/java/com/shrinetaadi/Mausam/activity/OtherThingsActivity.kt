package com.shrinetaadi.Mausam.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.fragment.AddCity
import com.shrinetaadi.Mausam.fragment.DailyFragment
import com.shrinetaadi.Mausam.fragment.Setting
import com.shrinetaadi.Mausam.model.WeatherResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherThingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_things)
        var arg = Bundle()
        if (intent != null) {
            arg = intent.getBundleExtra("arg")!!
        }
        var position = arg.getInt("val")
        var list =
            arg.getParcelableArrayList<WeatherResponse.Daily>("list") as ArrayList<WeatherResponse.Daily>
        when (position) {
            1 -> replaceFragment(DailyFragment(), list)
            2 -> replaceFragment(AddCity(), list)
            3 -> replaceFragment(Setting(), list)
        }


    }

    private fun replaceFragment(
        fragment: Fragment,
        listData: ArrayList<WeatherResponse.Daily>,
    ) {
        val arg = Bundle()
        arg.putParcelableArrayList("listData", listData)

        fragment.arguments = arg
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left
        )

        fragmentTransaction.replace(R.id.frameLayout, fragment)

        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            finish()
        }
    }
}
