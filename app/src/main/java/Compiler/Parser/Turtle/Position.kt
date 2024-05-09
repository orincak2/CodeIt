package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_SET_POSITION
import Compiler.Parser.Syntax
import Compiler.Parser.Variable


class Position(ntrt: Variable, nX: Syntax, nY: Syntax):Syntax(){
    var trt = ntrt
    var xx = nX
    var yy = nY
    override fun generate(){
        trt.generate()
        xx.generate()
        yy.generate()
        poke(INSTRUCTION_SET_POSITION)

    }
}