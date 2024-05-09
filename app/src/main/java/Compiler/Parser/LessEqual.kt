package Compiler.Parser

import Compiler.INSTRUCTION_LESSEQUAL

class LessEqual(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_LESSEQUAL)
    }
}