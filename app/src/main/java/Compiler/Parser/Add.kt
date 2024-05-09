package Compiler.Parser

import Compiler.INSTRUCTION_ADD

class Add(nl: Syntax, nr: Syntax):
    BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_ADD)
    }
}