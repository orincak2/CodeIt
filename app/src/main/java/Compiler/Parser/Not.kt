package Compiler.Parser

import Compiler.INSTRUCTION_NOT

class Not(ne:Syntax):UnaryOperation(ne){
    override fun generate() {
        e.generate()
        poke(INSTRUCTION_NOT)
    }
}