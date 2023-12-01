package Turtle

import Parser.INSTRUCTION_FD
import Parser.Syntax
import Parser.Variable

class Fd(ntrt: Variable, nParam: Syntax):TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_FD)

    }
}