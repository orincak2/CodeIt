package Compiler.Parser

import Compiler.INSTRUCTION_SUB

class Sub(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_SUB)
    }
}