package com.example.dp11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View

class Playground(internal var context: Context, attrs: AttributeSet)
    : View(context, attrs),
    View.OnKeyListener {
    var pwidth = 0
    var pheight = 0
    var ciara = emptyList<Float>().toMutableList()
    var cc = Color.RED
    init { // inicializacia
        setBackgroundColor(Color.GRAY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        pwidth = widthMeasureSpec
        pheight = heightMeasureSpec
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pwidth = w
        pheight = h
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(ciara.size > 2) {
            val mP = Paint()
            mP.color = cc
            mP.strokeWidth = 5.0F

            val l = emptyArray<Float>().toMutableList()
            for (i in ciara){
                l.add(i)
            }
            canvas.drawLines(l.toFloatArray(),mP)

        }

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)

    }
    override fun onKey(arg0: View, arg1: Int, arg2: KeyEvent): Boolean {
        when (arg1) {
//            KeyEvent.KEYCODE_DPAD_LEFT -> block.update(-100,0)
//            KeyEvent.KEYCODE_DPAD_RIGHT -> block.update(100,0)
            else -> return false
        }
        invalidate()
        return true
    }
}
