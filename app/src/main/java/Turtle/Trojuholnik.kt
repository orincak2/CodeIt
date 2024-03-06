package Turtle

import Parser.INSTRUCTION_LT
import Parser.INSTRUCTION_STVOREC
import Parser.INSTRUCTION_TROJUHOLNIK
import Parser.Syntax
import Parser.Variable

class Trojuholnik(ntrt: Variable, nX: Syntax, nY: Syntax, nVelk: Syntax, nFarb : Syntax):TurtleCommand(nX){
    var trt = ntrt
    var nnX = nX
    var nnY = nY
    var nnVelk = nVelk
    var nnFarb = nFarb

    override fun generate(){
        trt.generate()
        nnX.generate()
        nnY.generate()
        nnVelk.generate()
        nnFarb.generate()
        poke(INSTRUCTION_TROJUHOLNIK)

    }
}