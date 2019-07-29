package io.github.dellisd.quicksave

import android.os.Build
import android.widget.TextView
import androidx.annotation.StyleRes

@Suppress("DEPRECATION")
fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT < 23) {
        setTextAppearance(context, resId)
    } else {
        setTextAppearance(resId)
    }
}