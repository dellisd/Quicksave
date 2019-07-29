package io.github.dellisd.quicksave.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox
import io.github.dellisd.quicksave.BooleanSetting
import io.github.dellisd.quicksave.R
import io.github.dellisd.quicksave.setTextAppearanceCompat

class CheckBoxSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SettingView<Boolean, BooleanSetting>(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.checkbox_setting, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CheckBoxSetting, 0, 0)
            .apply(::applyStyle)
    }

    private fun applyStyle(style: TypedArray) {
        val title = findViewById<TextView>(R.id.setting_title)
        val caption = findViewById<TextView>(R.id.setting_caption)

        val titleAppearance = style.getResourceId(
            R.styleable.CheckBoxSetting_titleTextAppearance,
            com.google.android.material.R.style.TextAppearance_MaterialComponents_Body1
        )
        val captionAppearance = style.getResourceId(
            R.styleable.CheckBoxSetting_captionTextAppearance,
            com.google.android.material.R.style.TextAppearance_MaterialComponents_Caption
        )

        title.setTextAppearanceCompat(titleAppearance)
        caption.setTextAppearanceCompat(captionAppearance)

        /*val offsetStart = style.getDimensionPixelOffset(R.styleable.SwitchSetting_offset_start, 16)
        title.updateLayoutParams<LayoutParams> {
            marginStart = offsetStart
        }
        caption.updateLayoutParams<LayoutParams> {
            marginStart = offsetStart
        }

        val offsetEnd = style.getDimensionPixelOffset(R.styleable.SwitchSetting_offset_end, 16)
        findViewById<SwitchMaterial>(R.id.setting_switch).updateLayoutParams<LayoutParams> {
            marginEnd = offsetEnd
        }*/
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = true

    override fun onSettingBound(setting: BooleanSetting) {
        val checkbox = findViewById<MaterialCheckBox>(R.id.setting_checkbox)
        checkbox.isChecked = setting.value

        setOnClickListener {
            val newState = !setting.value
            checkbox.isChecked = newState
            setting.value = newState
        }

        findViewById<TextView>(R.id.setting_title).text = setting.config.title
        findViewById<TextView>(R.id.setting_caption).text = setting.config.caption

        if (setting.config.dependsOn != null) {
            setting.config.dependsOn.addListener {
                this.isEnabled = it
                checkbox.isEnabled = it
            }
        }

        setting.addListener { value ->
            checkbox.isChecked = value
        }
    }
}