package ru.lionzxy.printbox.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class SquareFrameLayout : FrameLayout {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var size = if (widthMeasureSpec < heightMeasureSpec) heightMeasureSpec else widthMeasureSpec
        size = MeasureSpec.getSize(size)

        val spec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        super.onMeasure(spec, spec)
    }
}