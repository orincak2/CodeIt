package Compiler.Parser

import Compiler.INSTRUCTION_IN

class In(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_IN)
    }
}