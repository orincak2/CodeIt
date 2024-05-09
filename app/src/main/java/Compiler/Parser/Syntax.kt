package Compiler.Parser

import Compiler.adr
import Compiler.mem
import Drawing.Turtle

open class Syntax(){
    open fun execute(){}
    open fun generate(){}

    fun poke(code : Number){
        mem[adr] = code.toFloat()
        adr += 1
    }
    fun poke(code : Turtle){
        mem[adr] = code
        adr += 1
    }
    fun poke(code : Syntax){
        mem[adr] = code
        adr += 1
    }
    fun poke(code : String){
        mem[adr] = code
        adr += 1
    }
    fun poke(code : MutableList<Syntax>){
        mem[adr] = code
        adr += 1
    }
}