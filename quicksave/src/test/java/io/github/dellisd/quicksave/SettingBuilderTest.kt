package io.github.dellisd.quicksave

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class SettingBuilderTest {

    @Test
    fun defaultsConfiguredCorrectly() {
        val settings = object : SettingsProvider by MapProvider() {
            val testSetting by booleanSetting { }
        }

        val (key, default, _, _, dependsOn) = settings.testSetting.config

        assertEquals(
            "By default, setting key should be equal to the name of the setting property",
            "testSetting",
            key
        )

        assertNull(default)
        assertNull(dependsOn)
    }

    @Test
    fun settingConfiguredCorrectly() {
        val settings = object : SettingsProvider by MapProvider() {
            val toggle by booleanSetting { }

            val testSetting by intSetting {
                key = "some_key"
                title = "Test Setting"
                caption = "A Caption"
                default = 6
                dependsOn(toggle)
            }

        }

        val (key, default, title, caption, dependsOn) = settings.testSetting.config
        assertEquals("some_key", key)
        assertEquals("Test Setting", title)
        assertEquals("A Caption", caption)
        assertEquals(6, default)
        assertEquals(settings.toggle, dependsOn)

    }
}