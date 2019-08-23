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
        val px15 = dip2px(15f)
        val px10 = dip2px(10f)
        contentTextView!!.setPaddingRelative(px15, px10, px15, px10)
        conRl.addView(contentTextView)
    }


}
