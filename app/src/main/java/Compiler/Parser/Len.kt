package Compiler.Parser

import Compiler.INSTRUCTION_LEN

class Len(nargs :Syntax):Syntax(){
    var args = nargs
    override fun generate() {
        args.generate()
        poke(INSTRUCTION_LEN)
    }
}