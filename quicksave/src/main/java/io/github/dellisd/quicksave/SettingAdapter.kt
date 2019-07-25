package io.github.dellisd.quicksave

import kotlin.reflect.KClass

class SettingAdapter<T : Any, R : Any>(
    val from: KClass<T>,
    val to: KClass<R>,
    private val encoder: (T) -> R,
    private val decoder: (R) -> T
) {
    fun encode(value: T): R = encoder(value)
    fun decode(value: R): T = decoder(value)
}

inline fun <reified T : Any, reified R : Any> settingAdapter(
    noinline encoder: (T) -> R,
    noinline decoder: (R) -> T
): SettingAdapter<T, R> {
    return SettingAdapter(T::class, R::class, encoder, decoder)
}