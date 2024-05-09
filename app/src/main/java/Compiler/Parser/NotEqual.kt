package Compiler.Parser

import Compiler.INSTRUCTION_NOTEQUAL

class NotEqual(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_NOTEQUAL)
    }
}