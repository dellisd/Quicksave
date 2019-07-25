package io.github.dellisd.quicksave

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class MapProvider : SettingsProvider {
    private val stringMap = mutableMapOf<String, String?>()
    private val intMap = mutableMapOf<String, Int>()
    private val longMap = mutableMapOf<String, Long>()
    private val booleanMap = mutableMapOf<String, Boolean>()
    private val floatMap = mutableMapOf<String, Float>()
    private val stringSetMap = mutableMapOf<String, Set<String>>()

    override fun <T> get(setting: Setting<T>): T {
        val (key, default) = setting.config

        return when (setting) {
            is StringSetting -> stringMap[key] ?: default as? String
            is IntSetting -> intMap[key] ?: default as? Int ?: 0
            is LongSetting -> longMap[key] ?: default as? Long ?: 0
            is BooleanSetting -> booleanMap[key] ?: default as? Boolean ?: false
            is FloatSetting -> floatMap[key] ?: default as? Float ?: 0f
            is StringSetSetting -> stringSetMap[key] ?: default as? Set<String>
        } as T
    }

    override fun <T> set(setting: Setting<T>, value: T) {
        val (key) = setting.config

        when (setting) {
            is StringSetting -> stringMap[key] = value as String?
            is IntSetting -> intMap[key] = value as Int
            is LongSetting -> longMap[key] = value as Long
            is BooleanSetting -> booleanMap[key] = value as Boolean
            is FloatSetting -> floatMap[key] = value as Float
            is StringSetSetting -> stringSetMap[key] = value as Set<String>
        }
    }
}