package com.example.dpt.bubbletextview.helper

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.dpt.bubbletextview.widget.LeBubbleView

class LeBubbleTextViewHelper {

    var bubbleView: LeBubbleView? = null
    var bubblePopupWindow: PopupWindow? = null

    private var mAnchor: View? = null
    private var mBubbleViewWidth: Int = 0
    private var mBubbleViewHeight: Int = 0

    fun init(anchor: View, bubbleViewLayoutRes: Int) {
        bubbleView = LayoutInflater.from(anchor.context).inflate(
            bubbleViewLayoutRes, null
        ) as LeBubbleView
        mAnchor = anchor
        bubblePopupWindow = PopupWindow(
            bubbleView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        bubblePopupWindow!!.isFocusable = false
        bubblePopupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bubbleView!!.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        mBubbleViewWidth = bubbleView!!.measuredWidth
        mBubbleViewHeight = bubbleView!!.measuredHeight
    }

    fun show(xCustomOffset: Int = 0, yCustomOffset: Int = 0) {

        val location = IntArray(2)
        mAnchor!!.getLocationInWindow(location)
        val arrowDirection = bubbleView!!.arrowDirection
        val xOffset: Int
        val yOffset: Int
        val anchorWidth = mAnchor!!.width
        val anchorHeight = mAnchor!!.height
        when (arrowDirection) {
            LeBubbleView.ArrowDirection.LEFT -> {
                xOffset = anchorWidth
                val arrowOffset =
                    mBubbleViewHeight / 2 - (mBubbleViewHeight * bubbleView!!.relative).toInt()
                yOffset = -(mBubbleViewHeight - anchorHeight) / 2 + arrowOffset
            }
            LeBubbleView.ArrowDirection.TOP -> {
                yOffset = anchorHeight
                val arrowOffset =
                    mBubbleViewWidth / 2 - (mBubbleViewWidth * bubbleView!!.relative).toInt()
                xOffset = -(mBubbleViewWidth - anchorWidth) / 2 + arrowOffset
            }
            LeBubbleView.ArrowDirection.BOTTOM -> {
                yOffset = -mBubbleViewHeight
                val arrowOffset =
                    mBubbleViewWidth / 2 - (mBubbleViewWidth * bubbleView!!.relative).toInt()
                xOffset = -(mBubbleViewWidth - anchorWidth) / 2 + arrowOffset
            }
            else -> {
                xOffset = -mBubbleViewWidth - 10
                val arrowOffset =
                    mBubbleViewHeight / 2 - (mBubbleViewHeight * bubbleView!!.relative).toInt()
                yOffset = -(mBubbleViewHeight - anchorHeight) / 2 + arrowOffset
            }
        }
        bubblePopupWindow!!.showAtLocation(
            mAnchor,
            Gravity.NO_GRAVITY,
            location[0] + xOffset + xCustomOffset,
            location[1] + yOffset + yCustomOffset
        )
    }

    fun dismissBubblePopupWindow() {
        bubblePopupWindow!!.dismiss()
    }
}
