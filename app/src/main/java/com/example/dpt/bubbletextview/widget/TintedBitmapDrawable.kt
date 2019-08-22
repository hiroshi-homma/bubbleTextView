package com.example.dpt.bubbletextview.widget

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable

/**
 * Created by dupengtao on 15/8/24.
 */
class TintedBitmapDrawable : BitmapDrawable {
    private var tint: Int = 0
    private var alpha1: Int = 0

    constructor(res: Resources, bitmap: Bitmap, tint: Int) : super(res, bitmap) {
        this.tint = tint
        this.alpha1 = Color.alpha(tint)
    }

    constructor(res: Resources, resId: Int, tint: Int) : super(
        res,
        BitmapFactory.decodeResource(res, resId)
    ) {
        this.tint = tint
        this.alpha1 = Color.alpha(tint)
    }

    override fun setTint(tint: Int) {
        this.tint = tint
        this.alpha1 = Color.alpha(tint)
    }

    override fun draw(canvas: Canvas) {
        val paint = paint
        if (paint.colorFilter == null) {
            paint.colorFilter = LightingColorFilter(tint, 0)
            paint.alpha = alpha
        }
        super.draw(canvas)
    }
}