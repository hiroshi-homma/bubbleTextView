package com.example.dpt.bubbletextview.widget

import android.graphics.drawable.ColorDrawable

/**
 * Created by dupengtao on 15/8/12.
 */
abstract class LeStateColorDrawable(color: Int) : ColorDrawable(color) {

    private var mPressed = false

    override fun onStateChange(state: IntArray): Boolean {

        val pressed = isPressed(state)
        if (mPressed != pressed) {
            mPressed = pressed
            onIsPressed(mPressed)
        }
        return true
    }

    protected abstract fun onIsPressed(isPressed: Boolean)

    override fun setState(stateSet: IntArray): Boolean {
        return super.setState(stateSet)
    }

    override fun isStateful(): Boolean {
        return true
    }

    private fun isPressed(state: IntArray?): Boolean {
        var pressed = false
        var i = 0
        val j = state?.size ?: 0
        while (i < j) {
            if (state!![i] == android.R.attr.state_pressed) {
                pressed = true
                break
            }
            i++
        }
        return pressed
    }
}
