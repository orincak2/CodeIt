package Drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
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

    fun addCiara(pole:MutableList<Float>, color: Int, hrubka:Float = 5F){
        ciary.add(Ciara(context,pole,color,hrubka))
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

    fun reset(){
        this.cc = Color.BLACK
        this.invalidate()
        this.setVisibility(View.INVISIBLE)
        this.ciara = emptyList<Float>().toMutableList()
        this.ciary = emptyList<Ciara>().toMutableList()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val mPaint = Paint()
        mPaint.color = Color.YELLOW
        mPaint.strokeWidth = 3F
        for(x in 0..10) {
            var pom = pwidth.toFloat() * x*100/1000
            canvas.drawLine(pom,0F,pom,10F,mPaint)
            canvas.drawText((x*100).toString(),pom,20F,mPaint)
            if(x!= 0) {
                canvas.drawLine(0F, pom, 10F, pom, mPaint)
                canvas.drawText((x * 100).toString(), 20F, pom, mPaint)
            }
        }
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
