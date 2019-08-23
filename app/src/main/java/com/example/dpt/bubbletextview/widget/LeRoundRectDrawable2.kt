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
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val mBoundsF: RectF
    private val mBoundsI: Rect

    init {
        paint.color = backgroundColor
        mBoundsF = RectF()
        mBoundsI = Rect()
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

}
