package io.github.dellisd.quicksave.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.switchmaterial.SwitchMaterial
import io.github.dellisd.quicksave.R
import io.github.dellisd.quicksave.Setting

@SuppressLint("ViewConstructor")
class SwitchSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var setting: Setting<Boolean>
    private val view: View = LayoutInflater.from(context).inflate(R.layout.switch_setting, this, false)

    init {
        addView(view)
        invalidate()
    }

    fun bindSetting(setting: Setting<Boolean>) {
        val switch = view.findViewById<SwitchMaterial>(R.id.setting_switch)
        switch.isChecked = setting.value

        view.setOnClickListener {
            val newState = !setting.value
            switch.isChecked = newState
            setting.value = newState
        }

        view.findViewById<TextView>(R.id.setting_title).text = setting.config.title
        view.findViewById<TextView>(R.id.setting_caption).text = setting.config.caption

        if (setting.config.dependsOn != null) {
            setting.config.dependsOn.addListener {
                this.isEnabled = it
                switch.isEnabled = it
            }
        }
    }
}

fun ConstraintSet.match(view: View, parentView: View) {
    connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
    connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
    connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
    connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
}