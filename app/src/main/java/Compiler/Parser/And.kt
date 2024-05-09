package Compiler.Parser

import Compiler.INSTRUCTION_AND

class And(nl: Syntax, nr: Syntax):
    BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_AND)
    }
}