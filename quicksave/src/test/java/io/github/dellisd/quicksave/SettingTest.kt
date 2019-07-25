package io.github.dellisd.quicksave

import org.junit.Assert.*
import org.junit.Test

class SettingTest {

    private val settings = object : SettingsProvider by MapProvider() {
        val testSetting by stringSetting {  }
    }

    @Test
    fun listenerCallbacksCalledWhenUpdated() {
        var settingUpdated: String? = null
        settings.testSetting.addListener {
            settingUpdated = it
        }

        settings.testSetting.value = "Hello World"

        assertEquals("Hello World", settingUpdated)
        assertEquals("Hello World", settings.testSetting.value)
    }

    @Test
    fun listenerRemoval() {
        var settingUpdated: String? = null
        val callback: (String) -> Unit = { settingUpdated = it }
        settings.testSetting.addListener(callback)
        settings.testSetting.removeListener(callback)

        settings.testSetting.value = "Hello World"
        assertNull(settingUpdated)
    }
}