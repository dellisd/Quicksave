package io.github.dellisd.quicksave

import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class SharedPreferenceProvider(private val sharedPreferences: SharedPreferences) : SettingsProvider {
    override fun <T> get(setting: Setting<T>): T {
        val (key, default) = setting.config

        return when (setting) {
            is StringSetting -> sharedPreferences.getString(key, default as? String)
            is IntSetting -> sharedPreferences.getInt(key, default as? Int ?: 0)
            is LongSetting -> sharedPreferences.getLong(key, default as? Long ?: 0)
            is BooleanSetting -> sharedPreferences.getBoolean(key, default as? Boolean ?: false)
            is FloatSetting -> sharedPreferences.getFloat(key, default as? Float ?: 0f)
            is StringSetSetting -> sharedPreferences.getStringSet(key, default as? Set<String>)
        } as T
    }

    override fun <T> set(setting: Setting<T>, value: T) {
        val (key) = setting.config

        when (setting) {
            is StringSetting -> sharedPreferences.edit().putString(key, value as String?).apply()
            is IntSetting -> sharedPreferences.edit().putInt(key, value as Int).apply()
            is LongSetting -> sharedPreferences.edit().putLong(key, value as Long).apply()
            is BooleanSetting -> sharedPreferences.edit().putBoolean(key, value as Boolean).apply()
            is FloatSetting -> sharedPreferences.edit().putFloat(key, value as Float).apply()
            is StringSetSetting -> sharedPreferences.edit().putStringSet(key, value as? Set<String>).apply()
        }
    }
}

