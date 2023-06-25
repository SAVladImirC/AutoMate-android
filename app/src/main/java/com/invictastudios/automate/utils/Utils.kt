package com.invictastudios.automate.utils

import androidx.navigation.NavOptions
import com.invictastudios.automate.R

object Utils {

    fun popUpLeftRightNavigation(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_right)
            .setExitAnim(R.anim.to_left)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right)
            .build()
    }

    fun popUpRightLeftNavigation(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.from_left)
            .setExitAnim(R.anim.to_right)
            .setPopEnterAnim(R.anim.from_right)
            .setPopExitAnim(R.anim.to_left)
            .build()
    }

    fun popUpDefaultNavigation(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            .build()
    }

    fun getReadableTime(duration: Int): String {
        val time: String
        val hrs = duration / (1000 * 60 * 60)
        val min = (duration % (1000 * 60 * 60)) / (1000 * 60)
        val secs = (((duration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000
        time = if (hrs < 1)
            String.format("%02d:%02d", min, secs)
        else
            String.format("%d:%02d:%02d", hrs, min, secs)
        return time
    }

}