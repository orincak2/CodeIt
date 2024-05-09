package Compiler.Parser

import Compiler.INSTRUCTION_MUL

class Mul(nl:Syntax, nr:Syntax): BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_MUL)
    }
}