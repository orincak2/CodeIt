package Compiler.Parser

import Compiler.INSTRUCTION_DIV

class Div(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_DIV)
    }
}