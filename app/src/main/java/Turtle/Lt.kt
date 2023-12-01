package Turtle

import Parser.INSTRUCTION_LT
import Parser.Syntax

class Lt(nParam: Syntax):TurtleCommand(nParam){

    override fun generate(){
        param.generate()
        poke(INSTRUCTION_LT)

    }
}