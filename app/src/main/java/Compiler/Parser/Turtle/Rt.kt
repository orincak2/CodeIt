package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_RT
import Compiler.Parser.Syntax
import Compiler.Parser.Variable


class Rt(ntrt: Variable, nParam: Syntax): TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_RT)

    }
}