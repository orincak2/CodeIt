package Drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

class Ciara(context : Context, val npole : MutableList<Float>, val nColor : Int, nhrubka:Float=7F) {
    var pole = npole
    var color = nColor
    var hrubka = nhrubka

    fun update() {
    }

    fun draw(canvas: Canvas) {
        val mPaint = Paint()
        mPaint.color = color
        mPaint.strokeWidth = hrubka
        //canvas.drawCircle((100/2).toFloat(), (100/2).toFloat(), (100/2).toFloat(), mPaint)
        canvas.drawLines(pole.toFloatArray(),mPaint)


    }
}