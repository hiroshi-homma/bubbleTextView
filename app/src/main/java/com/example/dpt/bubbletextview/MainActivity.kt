package com.example.dpt.bubbletextview

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.dpt.bubbletextview.helper.LeBubbleTextViewHelper
import com.example.dpt.bubbletextview.widget.LeBubbleTitleTextView


class MainActivity : Activity() {

    private var inited: Boolean = false
    private var bt1: Button? = null
    private var helper1: LeBubbleTextViewHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        bt1 = findViewById<View>(R.id.bt) as Button
        bt1!!.setOnClickListener { helper1!!.show() }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !inited) {
            inited = true

            helper1 = LeBubbleTextViewHelper()
            helper1!!.init(bt1!!, R.layout.view_demo_bubble_title1)
            helper1!!.show()
            val bubbleTitleTextView1 = helper1!!.bubbleView as LeBubbleTitleTextView
            bubbleTitleTextView1.setCancelImageOnClickListener(View.OnClickListener { helper1!!.dismissBubblePopupWindow() })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
