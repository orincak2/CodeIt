package com.example.dp11

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