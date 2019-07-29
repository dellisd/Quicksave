package io.github.dellisd.quicksave.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

open class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val constraintSet = ConstraintSet()
    protected val titleTextView: TextView = TextView(context).apply {
        addView(this)
        id = View.generateViewId()
        with (constraintSet) {
            addToVerticalChain(id, ConstraintSet.PARENT_ID, captionTextView.id)
            connect(id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        }
        text = "Hello"
    }
    protected val captionTextView: TextView = TextView(context).apply {
        addView(this)
        id = View.generateViewId()
        with (constraintSet) {
            addToVerticalChain(id, titleTextView.id, ConstraintSet.PARENT_ID)
            connect(id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        }
        text = "World"
    }

    init {

    }
}