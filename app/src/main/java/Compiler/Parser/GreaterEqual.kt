package Compiler.Parser

import Compiler.INSTRUCTION_GREATEREQUAL

class GreaterEqual(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_GREATEREQUAL)
    }
}