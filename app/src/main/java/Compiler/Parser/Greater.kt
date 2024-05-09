package Compiler.Parser

import Compiler.INSTRUCTION_GREATER

class Greater(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_GREATER)
    }
}