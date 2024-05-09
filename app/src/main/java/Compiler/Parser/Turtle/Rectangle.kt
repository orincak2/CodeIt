package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_RECTANGLE
import Compiler.Parser.Syntax


class Rectangle(nX: Syntax, nY: Syntax, nXX: Syntax, nYY: Syntax, nFarb : Syntax, nFarbit : Syntax):
    TurtleCommand(nX){
    var nnXX = nXX
    var nnX = nX
    var nnY = nY
    var nnYY = nYY
    var nnFarb = nFarb
    var nnFarbit = nFarbit

    override fun generate(){
        nnX.generate()
        nnY.generate()
        nnXX.generate()
        nnYY.generate()
        nnFarb.generate()
        nnFarbit.generate()
        poke(INSTRUCTION_RECTANGLE)

    }
}