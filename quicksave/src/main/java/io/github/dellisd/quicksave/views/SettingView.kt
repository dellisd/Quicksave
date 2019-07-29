package io.github.dellisd.quicksave.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.dellisd.quicksave.Setting

abstract class SettingView<T, S : Setting<T>> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    lateinit var setting: S
        private set

    fun bindSetting(setting: S) {
        this.setting = setting
        onSettingBound(setting)

        setting.addListener(::onSettingValueChanged)
    }

    protected abstract fun onSettingBound(setting: S)

    protected open fun onSettingValueChanged(value: T) {}
}