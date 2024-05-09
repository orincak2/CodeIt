package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_SET_COLOR
import Compiler.Parser.Syntax
import Compiler.Parser.Variable

class Farba(ntrt: Variable, nParam: Syntax): TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_SET_COLOR)

    }
}