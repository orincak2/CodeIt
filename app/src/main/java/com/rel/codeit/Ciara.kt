package com.rel.codeit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

class Ciara(context : Context, val npole : MutableList<Float>, val nColor : Int) {
    var pole = npole
    var color = nColor

    fun update() {
    }

    fun draw(canvas: Canvas) {
        val mPaint = Paint()
        mPaint.color = color
        mPaint.strokeWidth = 5.0F
        //canvas.drawCircle((x+size/2).toFloat(), (y+size/2).toFloat(), (size/2).toFloat(), mPaint)
        canvas.drawLines(pole.toFloatArray(),mPaint)


    }
}