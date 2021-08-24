package com.unicorn.refuel.ui.act.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unicorn.refuel.R

abstract class BaseAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_base)

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) return

        // Create a new Fragment to be placed in the activity layout
        val fragment = createFragment()

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        fragment.arguments = intent.extras

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment).commit()
    }

    abstract fun createFragment(): Fragment

}