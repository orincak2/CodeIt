package Turtle

import Parser.INSTRUCTION_LT
import Parser.Syntax
import Parser.Variable

class Lt(ntrt: Variable, nParam: Syntax):TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_LT)

    }
}