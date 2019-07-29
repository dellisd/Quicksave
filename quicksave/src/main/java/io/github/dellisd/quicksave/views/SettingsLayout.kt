package io.github.dellisd.quicksave.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import io.github.dellisd.quicksave.BooleanSetting
import io.github.dellisd.quicksave.Setting

class SettingsLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    /*init {
        orientation = VERTICAL
    }*/

    fun inflateSettings(vararg settings: Setting<*>) {
        settings.forEach { setting ->
            val view = SwitchSetting(context)
            view.bindSetting(setting as BooleanSetting)

            addView(view)
        }
    }
}