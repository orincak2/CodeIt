package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_FD
import Compiler.Parser.Syntax
import Compiler.Parser.Variable

class Fd(ntrt: Variable, nParam: Syntax): TurtleCommand(nParam){
    var trt = ntrt
    override fun generate(){
        trt.generate()
        param.generate()
        poke(INSTRUCTION_FD)

    }
}