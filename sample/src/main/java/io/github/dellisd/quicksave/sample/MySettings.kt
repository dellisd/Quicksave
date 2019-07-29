package io.github.dellisd.quicksave.sample

import io.github.dellisd.quicksave.SettingsProvider
import io.github.dellisd.quicksave.booleanSetting

class MySettings(provider: SettingsProvider) : SettingsProvider by provider {
    val test by booleanSetting {
        key = "HellooWorld!"
        title = "Test"
        caption = "This is just a test of the system"
        default = true
    }

    val other by booleanSetting {
        title = "Other"
        caption = "Some other thing"
        dependsOn(test)
    }
}