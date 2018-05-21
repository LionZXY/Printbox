package ru.lionzxy.printbox.view.splashscreen.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import ru.lionzxy.printbox.utils.Constants
import ru.lionzxy.printbox.view.intro.ui.IntroActivity
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.lionzxy.printbox.view.print.ui.PrintFragment

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        if (preference.getBoolean(Constants.PREFERENCE_FIRSTRUN, true)) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}