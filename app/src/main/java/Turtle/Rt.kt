package Turtle

import Parser.INSTRUCTION_RT
import Parser.Syntax

class Rt(nParam: Syntax):TurtleCommand(nParam){

    override fun generate(){
        param.generate()
        poke(INSTRUCTION_RT)

    }
}