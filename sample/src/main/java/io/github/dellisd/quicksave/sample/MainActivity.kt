package io.github.dellisd.quicksave.sample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.dellisd.quicksave.R
import io.github.dellisd.quicksave.SharedPreferenceProvider
import io.github.dellisd.quicksave.views.SettingsLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsLayout = findViewById<SettingsLayout>(R.id.settings)
        val provider =
            SharedPreferenceProvider(getSharedPreferences("test", Context.MODE_PRIVATE))
        val settings = MySettings(provider)

        settingsLayout.inflateSettings(settings.test, settings.other)
    }
}
