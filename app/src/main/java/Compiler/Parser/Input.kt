package Compiler.Parser

import Compiler.INSTRUCTION_INPUT
import Compiler.INSTRUCTION_PRINT

class Input(nargs :Syntax):Syntax(){
    var args = nargs
    override fun generate() {
        args.generate()
        poke(INSTRUCTION_PRINT)
        poke(INSTRUCTION_INPUT)
    }
}