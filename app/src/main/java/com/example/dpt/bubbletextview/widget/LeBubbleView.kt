package com.example.dpt.bubbletextview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
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
open class LeBubbleView : RelativeLayout, Runnable {

    protected lateinit var mContext: Context
    lateinit var arrowDirection: ArrowDirection
        protected set
    var relative: Float = 0.toFloat()
        protected set
    protected lateinit var conRl: RelativeLayout
    protected lateinit var arrowImage: ImageView
    protected var pressBackgroundColor: Int = 0
    protected var backGroundColor: Int = 0
    protected lateinit var norDrawable: TintedBitmapDrawable
    protected lateinit var pressDrawable: TintedBitmapDrawable
    var arrowOffset: Int = 0
        protected set
    protected var curStyle: Int = 0


    constructor(context: Context) : super(context) {
        initialize(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs, defStyleAttr)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mContext = context
        //TODO custom attribute
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LeBubbleTextView,
            defStyleAttr,
            R.style.LeBubbleTextView_Dark
        )
        val radius = a.getDimension(R.styleable.LeBubbleTextView_bubbleCornerRadius, 0f)
        backGroundColor = a.getColor(R.styleable.LeBubbleTextView_bubbleBackgroundColor, 0)

        pressBackgroundColor =
            a.getColor(R.styleable.LeBubbleTextView_bubbleBackgroundPressColor, 0)
        val textColor = a.getColor(R.styleable.LeBubbleTextView_bubbleTextColor, 0)
        val textSize = a.getDimension(R.styleable.LeBubbleTextView_bubbleTextSize, 0f)
        val contentText = a.getString(R.styleable.LeBubbleTextView_bubbleText)

        val intDirection = a.getInt(R.styleable.LeBubbleTextView_bubbleArrowDirection, 3)
        setCurDirection(intDirection)

        val relativePosition =
            a.getFraction(R.styleable.LeBubbleTextView_relativePosition, 1, 1, 0.3f)

        setRelativePosition(relativePosition)
        setCurThemeStyle(textColor)
        onInitialize(attrs, defStyleAttr, a)
        a.recycle()
        initContent(radius, backGroundColor, textColor, textSize, contentText)

    }

    protected open fun onInitialize(attrs: AttributeSet?, defStyleAttr: Int, a: TypedArray) {

    }

    fun setCurThemeStyle(textColor: Int) {

        val darkColor = mContext.resources.getColor(R.color.bubbleView_dark_text_color)
        val lightColor = mContext.resources.getColor(R.color.bubbleView_light_text_color)
        if (textColor == darkColor) {
            curStyle = STYLE_DARK
        } else if (textColor == lightColor) {
            curStyle = STYLE_LIGHT
        } else {
            curStyle = STYLE_OTHER
        }
    }

    private fun setRelativePosition(relativePosition: Float) {
        if (relativePosition < 0.2f) {
            relative = 0.2f
        } else if (relativePosition > 0.8f) {
            relative = 0.8f
        } else {
            relative = relativePosition
        }
    }

    private fun setCurDirection(intDirection: Int) {
        when (intDirection) {
            1 -> {
                arrowDirection = ArrowDirection.LEFT
            }
            2 -> {
                arrowDirection = ArrowDirection.TOP
            }
            3 -> {
                arrowDirection = ArrowDirection.RIGHT
            }
            4 -> {
                arrowDirection = ArrowDirection.BOTTOM
            }
        }
    }

    private fun initContent(
        radius: Float,
        backgroundColor: Int,
        textColor: Int,
        textSize: Float,
        content: String?
    ) {


        conRl = RelativeLayout(mContext)
        conRl.id = View.generateViewId()
        val conRlParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        //conRl.setLayoutParams(conRlParams);
        val roundRectDrawable = LeRoundRectDrawable2(backgroundColor, radius)
        conRl.background = roundRectDrawable

        initChildView(radius, backgroundColor, textColor, textSize, content)
        arrowImage = ImageView(mContext)
        arrowImage.id = View.generateViewId()
        val arrowParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        val r: Int
        when (arrowDirection) {
            LeBubbleView.ArrowDirection.LEFT -> {
                r = 180
                conRlParams.addRule(RelativeLayout.END_OF, arrowImage.id)
            }
            LeBubbleView.ArrowDirection.TOP -> {
                r = 270
                conRlParams.addRule(RelativeLayout.BELOW, arrowImage.id)
            }
            LeBubbleView.ArrowDirection.BOTTOM -> {
                r = 90
                arrowParams.addRule(RelativeLayout.BELOW, conRl.id)
            }
            else -> {
                r = 0
                arrowParams.addRule(RelativeLayout.END_OF, conRl.id)
            }
        }

        //int arrowRes = backgroundColor == Color.parseColor("#B3000000")?R.drawable.le_bubble_arrow_light:R.drawable.le_bubble_arrow_light;
        val arrowRes = R.drawable.le_bubble_arrow_light
        val source = BitmapFactory.decodeResource(this.resources, arrowRes)

        val rotateBitmap = rotateBitmap(source, r.toFloat())

        norDrawable = TintedBitmapDrawable(mContext.resources, rotateBitmap, backgroundColor)
        pressDrawable = TintedBitmapDrawable(mContext.resources, rotateBitmap, pressBackgroundColor)

        arrowImage.setImageDrawable(norDrawable)
        this.addView(arrowImage, arrowParams)
        this.addView(conRl, conRlParams)

        arrowImage.imageTintList = ColorStateList.valueOf(backgroundColor)

        background = object : LeStateColorDrawable(Color.TRANSPARENT) {
            override fun onIsPressed(isPressed: Boolean) {
                val conRlBackground = conRl.background as LeRoundRectDrawable2
                if (isPressed) {
                    conRlBackground.paint.color = pressBackgroundColor
                    //arrowImage.setImageTintList(ColorStateList.valueOf(pressBackgroundColor));
                    arrowImage.setImageDrawable(pressDrawable)
                } else {
                    conRlBackground.paint.color = backgroundColor
                    //arrowImage.setImageTintList(ColorStateList.valueOf(backgroundColor));
                    arrowImage.setImageDrawable(norDrawable)
                }
                conRl.invalidate()
                arrowImage.invalidate()
            }
        }

        conRl.post(this)

        this.isClickable = true

    }

    protected open fun initChildView(
        radius: Float,
        backgroundColor: Int,
        textColor: Int,
        textSize: Float,
        content: String?
    ) {

    }

    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    protected fun dip2px(dpValue: Float): Int {
        val scale = mContext.resources
            .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun run() {
        val conRlw = conRl.width
        val conRlh = conRl.height
        val params = arrowImage.layoutParams as RelativeLayout.LayoutParams
        when (arrowDirection) {
            LeBubbleView.ArrowDirection.TOP, LeBubbleView.ArrowDirection.BOTTOM -> {
                arrowOffset = (conRlw * relative - arrowImage.width / 2).toInt()
                params.setMargins(arrowOffset, 0, 0, 0)
            }
            LeBubbleView.ArrowDirection.LEFT -> {
                arrowOffset = (conRlh * relative - arrowImage.height / 2).toInt()
                params.setMargins(0, arrowOffset, 0, 0)
            }
            else -> {
                arrowOffset = (conRlh * relative - arrowImage.height / 2).toInt()
                params.setMargins(0, arrowOffset, 0, 0)
            }
        }

        onPostCallBack(conRlw, conRlh)
    }

    protected open fun onPostCallBack(conRlw: Int, conRlh: Int) {

    }

    enum class ArrowDirection {
        LEFT, TOP, RIGHT, BOTTOM
    }

    companion object {

        val STYLE_DARK = 1
        val STYLE_LIGHT = 2
        val STYLE_OTHER = 3
    }
}
