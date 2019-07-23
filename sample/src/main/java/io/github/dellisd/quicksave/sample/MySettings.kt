package io.github.dellisd.quicksave.sample

import io.github.dellisd.quicksave.Setting
import io.github.dellisd.quicksave.SettingsProvider
import io.github.dellisd.quicksave.setting

class MySettings(provider: SettingsProvider) : SettingsProvider by provider {
    val test: Setting<Boolean> by setting {
        key = "HellooWorld!"
        title = "Test"
        caption = "This is just a test of the system"
        default = true
    }

    val other: Setting<Boolean> by setting {
        title = "Other"
        caption = "Some other thing"
        dependsOn(test)
    }
}