package com.example.dpt.bubbletextview.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView


/**
 * Created by dupengtao on 15/7/25.
 *
 *
 * <declare-styleable name="LeBubbleTextView">
 *
 * <attr name="bubbleCornerRadius" format="dimension"></attr>
 *
 * <attr name="bubbleBackgroundColor" format="color"></attr>
 *
 * <attr name="bubbleTextSize" format="dimension"></attr>
 *
 * <attr name="bubbleTextColor" format="color"></attr>
 *
 * <attr name="bubbleText" format="string"></attr>
 *
 *
 *
 * <attr name="bubbleArrowDirection">
 * <enum name="left" value="1"></enum>
 * <enum name="top" value="2"></enum>
 * <enum name="right" value="3"></enum>
 * <enum name="bottom" value="4"></enum>
</attr> *
 *
 * <attr name = "relativePosition" format = "fraction"></attr>
 *
 * <attr name="bubbleBackgroundPressColor" format="color"></attr>
</declare-styleable> *
 */
class LeBubbleTextView : LeBubbleView, Runnable {

    var contentTextView: TextView? = null
        private set

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

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
