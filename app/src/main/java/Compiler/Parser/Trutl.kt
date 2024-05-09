package Compiler.Parser

import Compiler.INSTRUCTION_PUSH
import Drawing.Turtle

class Trutl(nturtle: Turtle):Syntax() {
    var turtle = nturtle

    override fun generate() {
        poke(INSTRUCTION_PUSH)
        poke(turtle)
    }
}