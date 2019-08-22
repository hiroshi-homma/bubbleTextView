package com.example.dpt.bubbletextview

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.example.dpt.bubbletextview.helper.LeBubbleTextViewHelper


class MainActivity : Activity() {


    private var inited: Boolean = false
    private var bt1: Button? = null
    private var bt2: Button? = null
    private var bt3: Button? = null
    private var bt4: Button? = null
    private var helper1: LeBubbleTextViewHelper? = null
    private var helper2: LeBubbleTextViewHelper? = null
    private var helper3: LeBubbleTextViewHelper? = null
    private var helper4: LeBubbleTextViewHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        bt1 = findViewById<View>(R.id.bt) as Button
        bt1!!.setOnClickListener { helper1!!.show() }

        bt2 = findViewById<View>(R.id.bt2) as Button
        bt2!!.setOnClickListener { helper2!!.show() }

        bt3 = findViewById<View>(R.id.bt3) as Button
        bt3!!.setOnClickListener { helper3!!.show() }

        bt4 = findViewById<View>(R.id.bt4) as Button
        bt4!!.setOnClickListener { helper4!!.show() }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !inited) {
            inited = true

            helper1 = LeBubbleTextViewHelper()
            helper1!!.init(bt1!!, R.layout.view_demo_bubble1)
            helper1!!.show()
            helper1!!.bubbleTextView!!.setOnClickListener { helper1!!.dismissBubblePopupWindow() }

            helper2 = LeBubbleTextViewHelper()
            helper2!!.init(bt2!!, R.layout.view_demo_bubble2)
            helper2!!.show()
            helper2!!.bubbleTextView!!.setOnClickListener { helper2!!.dismissBubblePopupWindow() }

            helper3 = LeBubbleTextViewHelper()
            helper3!!.init(bt3!!, R.layout.view_demo_bubble3)
            helper3!!.show()
            helper3!!.bubbleTextView!!.setOnClickListener { helper3!!.dismissBubblePopupWindow() }

            helper4 = LeBubbleTextViewHelper()
            helper4!!.init(bt4!!, R.layout.view_demo_bubble4)
            helper4!!.show()
            helper4!!.bubbleTextView!!.setOnClickListener { helper4!!.dismissBubblePopupWindow() }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
