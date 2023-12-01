package com.example.dp11

import android.graphics.Color
import android.view.View
import kotlin.random.Random



class Turtle(pg: Playground, xx: Double = 400.0, yy:Double = 400.0) {

    var randomx: Double = 100.0 + Random.nextDouble() * (400.0 - 100.0)
    var randomy: Double = 100.0 + Random.nextDouble() * (400.0 - 100.0)
    var x = randomx
    var y = randomy
    var playground = pg
    var uhol = 0.toFloat()
    var color = Color.RED
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
        x = xx
        y = yy
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
        if(far == "cervena"){
            color = Color.YELLOW
        }
        else if (far == "modra"){
            color = Color.BLUE
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
        x = 400.0
        y = 400.0
        uhol = 0.toFloat()
        playground.cc = color
        playground.invalidate()
    }
}