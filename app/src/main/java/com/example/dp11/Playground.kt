package com.example.dp11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import java.util.concurrent.CountDownLatch

class Playground(internal var context: Context, attrs: AttributeSet)
    : View(context, attrs),
    View.OnKeyListener {
    var pwidth = 0
    var pheight = 0
    var ciara = emptyList<Float>().toMutableList()
    var ciary = emptyList<Ciara>().toMutableList()
    var cc = Color.RED
    private var latch: CountDownLatch? = null
    init { // inicializacia
        setBackgroundColor(Color.GRAY)
    }

    fun addCiara(pole:MutableList<Float>, color: Int){
        ciary.add(Ciara(context,pole,color))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //pwidth = widthMeasureSpec
        //pheight = heightMeasureSpec
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(pwidth == 0) {
            pwidth = w
            pheight = h
        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (b in ciary) {
            b.draw(canvas)
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
    fun waitForInvalidate() {
        this.invalidate()
        latch = CountDownLatch(1)

        // Pridáme poslucháča na sledovanie dokončenia invalidácie
        viewTreeObserver.addOnDrawListener {
            latch?.countDown() // Označíme, že invalidácia je dokončená
            latch = null // Uvoľníme zámok
        }

        // Čakáme, kým invalidate() nie je dokončené
        latch?.await()
    }
}
