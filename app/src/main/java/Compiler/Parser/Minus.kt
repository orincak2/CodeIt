package Compiler.Parser

import Compiler.INSTRUCTION_MINUS

class Minus(ne:Syntax):UnaryOperation(ne){
    override fun generate() {
        e.generate()
        poke(INSTRUCTION_MINUS)
    }
}