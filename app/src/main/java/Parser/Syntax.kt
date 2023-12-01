package Parser

import com.example.dp11.Turtle
import kotlin.collections.List



open class Syntax(){
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

    fun reset(){
        pc = 0
        top = mem.size-1
        frame = top
        terminated = false
    }
    open fun execute(){}
    open fun generate(){}
}