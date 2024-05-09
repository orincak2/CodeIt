package Drawing

import android.graphics.Color
import android.view.View
import kotlin.random.Random



class Turtle(pg: Playground, xx: Float = 0F, yy:Float = 0F, colorn: String = "red") {

    var randomx: Double = 100.0 + Random.nextDouble() * (400.0 - 100.0)
    var randomy: Double = 100.0 + Random.nextDouble() * (400.0 - 100.0)
    var x = xx
    var y = yy
    var playground = pg
    var uhol = 0.toFloat()
    var color = Color.RED
    init {
        farba(colorn)
    }

    fun dopredu(iD : Float){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        var u = Math.toRadians(uhol.toDouble())
        var xx = x + iD * Math.cos(u)
        var yy = y + iD * Math.sin(u)
        var pom = emptyList<Float>().toMutableList()
        pom.add(x.toFloat())
        pom.add(y.toFloat())
        pom.add(xx.toFloat())
        pom.add(yy.toFloat())
        playground.addCiara(pom,color)
        x = xx.toFloat()
        y = yy.toFloat()
    }

    fun ciara(x:Float, y: Float, xx: Float, yy: Float, farva: String, hubka:Float = 5F){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        farba(farva)
        var pom = emptyList<Float>().toMutableList()
        pom.add(x.toFloat())
        pom.add(y.toFloat())
        pom.add(xx.toFloat())
        pom.add(yy.toFloat())
        playground.addCiara(pom,color,hubka)
    }
    fun vlavo(u : Float){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        if(uhol - u > 0) {
            uhol -= u
        }
        else{
            uhol = (uhol - u) + 360
        }

       // playground.invalidate()
    }
    fun vpravo(u : Float){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        if(uhol + u < 360) {
            uhol += u
        }
        else{
            uhol = uhol + u -360
        }
        //playground.invalidate()
    }

    fun stvorec(xx : Float, yy: Float, velkost : Float, farva: String){
        obdlznik(xx, yy, xx+velkost,yy-velkost,farva,0F)
    }
    fun obdlznik(xx : Float, yy: Float, xxx : Float,yyy: Float, farva: String, farbit:Float){
        ciara(xx,yy,xxx,yy, farva)
        ciara(xx,yyy,xxx,yyy, farva)
        ciara(xx,yy,xx,yyy,farva)
        ciara(xxx,yy,xxx,yyy, farva)
        if(farbit == 1F) {
            var pom = 0F
            if (xxx > xx) {
                pom = xxx - xx
            } else {
                pom = xx - xxx
            }
            for (i in 2..pom.toInt() - 2) {
                ciara(xx + i, yy, xx + i, yyy, farva)
            }
        }


    }
    fun trojuholnik(xx : Float, yy: Float, velkost : Float, farva: String){
        var x = xx
        var y = yy
        var pomX = xx+velkost
        var pomY = yy
        ciara(x,y,pomX,pomY,farva)
        x = pomX
        y = pomY
        pomX = (pomX + velkost * Math.cos(Math.toRadians(240.0))).toFloat()
        pomY = (pomY + velkost * Math.sin(Math.toRadians(240.0))).toFloat()
        ciara(x,y,pomX,pomY, farva)
        x = pomX
        y = pomY
        pomX = (pomX + velkost * Math.cos(Math.toRadians(1200.0))).toFloat()
        pomY = (pomY + velkost * Math.sin(Math.toRadians(1200.0))).toFloat()
        ciara(x,y,pomX,pomY, farva)
    }

    fun kruh(xx : Float, yy: Float, velkost : Float, farva: String){
        oval(xx-velkost,yy-velkost,xx+velkost,yy+velkost, farva,0F)
    }

    fun oval(xx : Float, yy: Float, xxx : Float,yyy:Float, farva: String, farbit:Float){
        val centerX = xx + (xxx-xx)/2 // x-ova suradnica streda oválu
        val centerY = yy + (yyy-yy)/2
        val rx = (xxx-xx-5)/2 // polomer šírky oválu
        val ry = (yyy-yy-5)/2
        oval(xx,yy,xxx,yyy,farva)
        if(farbit == 1F) {
            for (i in 0 .. 360) {
                val theta = Math.toRadians(i.toDouble()) // Prevod uhla na radiany
                val x = centerX + rx * Math.cos(theta)
                val y = centerY + ry * Math.sin(theta)

                // Nakresli úsečku od (centerX, centerY) do (x, y)
                ciara(centerX, centerY, x.toFloat(), y.toFloat(),farva,12F)
            }
        }
    }
    fun oval(xx : Float, yy: Float, xxx : Float,yyy:Float, farva: String){
        val centerX = xx + (xxx-xx)/2 // x-ova suradnica streda oválu
        val centerY = yy + (yyy-yy)/2 // y-ova suradnica streda oválu
        val rx = (xxx-xx)/2 // polomer šírky oválu
        val ry = (yyy-yy)/2 // polomer výšky oválu
        val steps = 360 // počet krokov

        val circumference = 2 * Math.PI * Math.sqrt((rx * rx + ry * ry) / 2.0)
        val stepLength = circumference / steps

// Počiatočná pozícia
        var currentAngle = 0.0
        var xPrev = centerX + rx * Math.cos(Math.toRadians(currentAngle))
        var yPrev = centerY + ry * Math.sin(Math.toRadians(currentAngle))

// Nakreslenie oválu v krokoch
        for (i in 1..steps) {
            currentAngle += 360.0 / steps
            val x = centerX + rx * Math.cos(Math.toRadians(currentAngle))
            val y = centerY + ry * Math.sin(Math.toRadians(currentAngle))

            // Nakresli úsečku od (xPrev, yPrev) do (x, y)
            ciara(xPrev.toFloat(), yPrev.toFloat(), x.toFloat(), y.toFloat(), "black")

            xPrev = x
            yPrev = y
        }
    }

    fun farba(far: String){
        if(far == "cervena" || far == "red"){
            color = Color.RED
        }else if (far == "zlta" || far == "yellow"){
            color = Color.YELLOW
        }
        else if (far == "modra" || far == "blue"){
            color = Color.BLUE
        }
        else if (far == "green" || far == "zelena"){
            color = Color.GREEN
        }
        else if (far == "biela" || far == "white"){
            color = Color.WHITE
        }
        else if (far == "oranzova" || far == "orange"){
            color = Color.rgb(255, 165, 0).toInt()
        }
        else{
            color = Color.BLACK
        }
    }
    fun reset(){
        playground.cc = Color.YELLOW
        playground.invalidate()
        playground.setVisibility(View.INVISIBLE)
        playground.ciara = emptyList<Float>().toMutableList()
        playground.ciary = emptyList<Ciara>().toMutableList()
        x = 0F
        y = 0F
        uhol = 0.toFloat()
        playground.cc = color
        playground.invalidate()
        //playground.waitForInvalidate()
    }

}