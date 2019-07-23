package io.github.dellisd.quicksave

class MapProvider : SettingsProvider {
    private val stringMap = mutableMapOf<String, String?>()
    private val intMap = mutableMapOf<String, Int>()
    private val longMap = mutableMapOf<String, Long>()
    private val booleanMap = mutableMapOf<String, Boolean>()
    private val floatMap = mutableMapOf<String, Float>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(setting: Setting<T>): T {
        return when (setting.type) {
            String::class -> stringMap[setting.key] ?: setting.default as? String
            Int::class -> intMap[setting.key] ?: setting.default as? Int ?: 0
            Long::class -> longMap[setting.key] ?: setting.default as? Long ?: 0
            Boolean::class -> booleanMap[setting.key] ?: setting.default as? Boolean ?: false
            Float::class -> floatMap[setting.key] ?: setting.default as? Float ?: 0f
            else -> throw IllegalArgumentException("No way to get ${setting.type.java} from Map")
        } as T
    }

    override fun <T : Any> set(setting: Setting<T>, value: T) {
        when (setting.type) {
            String::class -> stringMap[setting.key] = value as String?
            Int::class -> intMap[setting.key] = value as Int
            Long::class -> longMap[setting.key] = value as Long
            Boolean::class -> booleanMap[setting.key] = value as Boolean
            Float::class -> floatMap[setting.key] = value as Float
            else -> throw IllegalArgumentException("No way to put ${setting.type.java} into Map")
        }
    }
}