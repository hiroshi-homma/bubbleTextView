package com.example.dpt.bubbletextview.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.TouchDelegate
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.dpt.bubbletextview.R


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
class LeBubbleTitleTextView : LeBubbleView, Runnable {

    private var cancelImage: ImageView? = null
    private var titleTextView: TextView? = null
    private var contentTextView: TextView? = null
    private var titleText: String? = null


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInitialize(attrs: AttributeSet?, defStyleAttr: Int, a: TypedArray) {
        titleText = a.getString(R.styleable.LeBubbleTextView_bubbleTitleText)
    }

    private fun initCancelImageView() {
        cancelImage = ImageView(mContext)
    }

    private fun initContentTextView(textColor: Int, content: String?) {
        contentTextView = TextView(mContext)
        contentTextView!!.id = View.generateViewId()
        val contentTvParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        contentTvParams.setMargins(
            dip2px(22f),
            dip2px(1f),
            dip2px(22f),
            dip2px(15f)
        )
        contentTvParams.addRule(BELOW, titleTextView!!.id)
        contentTextView!!.layoutParams = contentTvParams
        contentTextView!!.setTextColor(textColor)
        contentTextView!!.textSize = 14f

        contentTextView!!.text = content
    }

    private fun initTitleTextView(textColor: Int) {
        titleTextView = TextView(mContext)
        titleTextView!!.id = View.generateViewId()
        val titleTvParams = LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )
        titleTvParams.setMargins(dip2px(15f), dip2px(10f), dip2px(15f), 0)
        titleTextView!!.layoutParams = titleTvParams
        titleTextView!!.isSingleLine = true
        //dark
        if (curStyle == LeBubbleView.STYLE_DARK) {
            titleTextView!!.setTextColor(Color.WHITE)
        } else {
            titleTextView!!.setTextColor(Color.BLACK)
        }
        titleTextView!!.textSize = 12f

        titleTextView!!.text = titleText
    }

    private fun initCancelView(conRlw: Int) {
        cancelImage!!.id = View.generateViewId()
        val cancelParams = LayoutParams(dip2px(10f), dip2px(10f))

        cancelParams.addRule(ALIGN_PARENT_TOP)
        cancelParams.setMargins(conRlw - dip2px(15f), dip2px(8f), dip2px(8f), 0)
        cancelImage!!.layoutParams = cancelParams

        val cancel = R.drawable.le_bubble_cancel
        val source = BitmapFactory.decodeResource(this.resources, cancel)
        val color: Int
        if (curStyle == STYLE_DARK) {
            color = Color.parseColor("#99FFFFFF")
        } else {
            color = Color.parseColor("#99000000")
        }
        val cancelBitmapDrawable = TintedBitmapDrawable(mContext.resources, source, color)
        cancelImage!!.setImageDrawable(cancelBitmapDrawable)
        increaseClickArea()
    }

    private fun increaseClickArea() {
        val conRlRect = Rect()
        conRl.getHitRect(conRlRect)
        val rect = Rect(conRlRect)
        rect.left = rect.right / 2
        conRl.touchDelegate = TouchDelegate(rect, cancelImage)
    }


    override fun initChildView(
        radius: Float,
        backgroundColor: Int,
        textColor: Int,
        textSize: Float,
        content: String?
    ) {
        super.initChildView(radius, backgroundColor, textColor, textSize, content)
        initCancelImageView()
        initTitleTextView(textColor)
        conRl.addView(titleTextView)
        initContentTextView(textColor, content)
        conRl.addView(contentTextView)
    }

    fun setCancelImageOnClickListener(l: OnClickListener) {
        cancelImage!!.setOnClickListener(l)
    }

    override fun onPostCallBack(conRlw: Int, conRlh: Int) {
        super.onPostCallBack(conRlw, conRlh)
        initCancelView(conRlw)
        conRl.addView(cancelImage)
    }
}
