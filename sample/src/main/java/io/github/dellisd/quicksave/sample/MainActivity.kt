package io.github.dellisd.quicksave.sample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.dellisd.quicksave.R
import io.github.dellisd.quicksave.SharedPreferenceProvider
import io.github.dellisd.quicksave.views.CheckBoxSetting
import io.github.dellisd.quicksave.views.SwitchSetting

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val settingsLayout = findViewById<SettingsLayout>(R.id.settings)
        val provider =
            SharedPreferenceProvider(getSharedPreferences("test", Context.MODE_PRIVATE))
        val settings = MySettings(provider)

        findViewById<SwitchSetting>(R.id.switch_setting).bindSetting(settings.test)
        findViewById<CheckBoxSetting>(R.id.checkbox_setting).bindSetting(settings.test)

        //settingsLayout.inflateSettings(settings.test, settings.other)
    }
}
