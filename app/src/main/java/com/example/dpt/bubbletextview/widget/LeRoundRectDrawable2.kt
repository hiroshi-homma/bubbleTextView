/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dpt.bubbletextview.widget

import android.graphics.*
import android.graphics.drawable.Drawable


/**
 * Very simple drawable that draws a rounded rectangle background with arbitrary corners and also
 * reports proper outline for L.
 *
 *
 * Simpler and uses less resources compared to GradientDrawable or ShapeDrawable.
 */
class LeRoundRectDrawable2(backgroundColor: Int, private var mRadius: Float) : Drawable() {
    val paint: Paint
    private val mBoundsF: RectF
    private val mBoundsI: Rect
    internal var padding: Float = 0.toFloat()
        private set
    private var mInsetForPadding = false
    private var mInsetForRadius = true

    var radius: Float
        get() = mRadius
        internal set(radius) {
            if (radius == mRadius) {
                return
            }
            mRadius = radius
            updateBounds(null)
            invalidateSelf()
        }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        paint.color = backgroundColor
        mBoundsF = RectF()
        mBoundsI = Rect()
    }

    internal fun setPadding(padding: Float, insetForPadding: Boolean, insetForRadius: Boolean) {
        if (padding == this.padding && mInsetForPadding == insetForPadding &&
            mInsetForRadius == insetForRadius
        ) {
            return
        }
        this.padding = padding
        mInsetForPadding = insetForPadding
        mInsetForRadius = insetForRadius
        updateBounds(null)
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(mBoundsF, mRadius, mRadius, paint)
    }

    private fun updateBounds(bounds: Rect?) {
        var bounds = bounds
        if (bounds == null) {
            bounds = getBounds()
        }
        mBoundsF.set(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat()
        )
        mBoundsI.set(bounds)
        //if (mInsetForPadding) {
        //float vInset = LeRoundRectDrawable2WithShadow.calculateVerticalPadding(mPadding, mRadius, mInsetForRadius);
        //float hInset = LeRoundRectDrawable2WithShadow.calculateHorizontalPadding(mPadding, mRadius, mInsetForRadius);
        //mBoundsI.inset((int) Math.ceil(hInset), (int) Math.ceil(vInset));
        // to make sure they have same bounds.
        //mBoundsF.set(mBoundsI);
        //}
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        updateBounds(bounds)
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(mBoundsI, mRadius)
    }

    override fun setAlpha(alpha: Int) {
        // not supported because older versions do not support
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // not supported because older versions do not support
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    fun setColor(color: Int) {
        paint.color = color
        invalidateSelf()
    }
}
