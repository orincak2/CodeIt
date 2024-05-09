package Compiler.Parser.Turtle

import Compiler.INSTRUCTION_TROJUHOLNIK
import Compiler.Parser.Syntax


class Trojuholnik(nX: Syntax, nY: Syntax, nVelk: Syntax, nFarb : Syntax): TurtleCommand(nX){
    var nnX = nX
    var nnY = nY
    var nnVelk = nVelk
    var nnFarb = nFarb

    override fun generate(){
        nnX.generate()
        nnY.generate()
        nnVelk.generate()
        nnFarb.generate()
        poke(INSTRUCTION_TROJUHOLNIK)

    }
}