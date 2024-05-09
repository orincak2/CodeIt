package Compiler.Parser

import Compiler.INSTRUCTION_PRINT

class Print(nexp: Syntax):Syntax(){
    var exp = nexp
    override fun generate() {
        exp.generate()
        poke(INSTRUCTION_PRINT)
    }
}