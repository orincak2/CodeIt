package Turtle

import Parser.INSTRUCTION_SET_COLOR
import Parser.Syntax
import Parser.Variable

class Farba(ntrt: Variable, nParam: Syntax):TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_SET_COLOR)

    }
}