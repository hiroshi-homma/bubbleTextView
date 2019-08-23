package com.example.dpt.bubbletextview.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView

class LeBubbleTextView : LeBubbleView, Runnable {

    var contentTextView: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun initChildView(
        radius: Float,
        backgroundColor: Int,
        textColor: Int,
        textSize: Float,
        content: String?
    ) {
        super.initChildView(radius, backgroundColor, textColor, textSize, content)
        contentTextView = TextView(mContext)
        contentTextView!!.id = View.generateViewId()
        contentTextView!!.setTextColor(textColor)
        contentTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        contentTextView!!.text = content
        val px22 = dip2px(21f)
        val px16 = dip2px(15f)
        contentTextView!!.setPaddingRelative(px22, px16, px22, px16)
        conRl.addView(contentTextView)
    }


}
