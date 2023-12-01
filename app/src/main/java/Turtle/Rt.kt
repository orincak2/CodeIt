package Turtle

import Parser.INSTRUCTION_RT
import Parser.Syntax
import Parser.Variable

class Rt(ntrt: Variable, nParam: Syntax):TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_RT)

    }
}