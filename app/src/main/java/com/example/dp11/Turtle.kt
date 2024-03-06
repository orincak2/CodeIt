package com.example.dp11

import android.graphics.Color
import android.view.View
import java.lang.Math.sqrt
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
    fun vlavo(u : Float){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        if(uhol + u < 360) {
            uhol += u
        }
        else{
            uhol = uhol + u -360
        }
       // playground.invalidate()
    }
    fun vpravo(u : Float){
        if(playground.visibility == View.INVISIBLE){
            playground.setVisibility(View.VISIBLE)
        }
        if(uhol - u > 0) {
            uhol -= u
        }
        else{
            uhol = (uhol - u) + 360
        }
        //playground.invalidate()
    }

    fun stvorec(xx : Float, yy: Float, velkost : Float, farva: String){
        var puhol = uhol
        var pfarva = color
        var px = x
        var py = y
        uhol = 0F
        x = xx-(velkost/2)
        y = yy+(velkost/2)
        farba(farva)
        for (x in 1..4){
            dopredu(velkost)
            vpravo(90F)
        }
        x = px
        y = py
        color = pfarva
        uhol = puhol
    }
    fun trojuholnik(xx : Float, yy: Float, velkost : Float, farva: String){
        var puhol = uhol
        var pfarva = color
        var px = x
        var py = y
        uhol = 0F
        x = xx-(velkost/2)
        y = yy+((velkost * sqrt(3.0)) / 4).toFloat()
        farba(farva)
        for (x in 1..3){
            dopredu(velkost)
            vpravo(120F)
        }
        x = px
        y = py
        color = pfarva
        uhol = puhol
    }

    fun kruh(xx : Float, yy: Float, velkost : Float, farva: String){
        var puhol = uhol
        var pfarva = color
        var px = x
        var py = y
        x = xx+(velkost)
        y = yy
        uhol = 0F
        vpravo(90F)
        farba(farva)
        var obvod = 2* 3.141592653589 * velkost
        for (x in 1..360){
            dopredu(obvod.toFloat()/360)
            vpravo(1F)
        }
        x = px
        y = py
        color = pfarva
        uhol = puhol
        vlavo(90F)
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
        else if (far == "biela" || far == "while"){
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
    }
}