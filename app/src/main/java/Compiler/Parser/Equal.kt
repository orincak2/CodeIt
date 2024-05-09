package Compiler.Parser

import Compiler.INSTRUCTION_EQUAL

class Equal(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_EQUAL)
    }
}