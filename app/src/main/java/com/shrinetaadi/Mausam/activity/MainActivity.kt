package com.shrinetaadi.Mausam.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shrinetaadi.Mausam.R
import com.shrinetaadi.Mausam.fragment.HomeFrag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        replaceFragment(HomeFrag(), true)
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransaction.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransaction.replace(R.id.frameLayout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()

    }
}