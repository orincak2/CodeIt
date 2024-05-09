package Compiler.Parser

import Compiler.INSTRUCTION_RANDIT

class Randit(nargs :Syntax, nbrgs :Syntax,):Syntax(){
    var args = nargs
    var brgs = nbrgs
    override fun generate() {
        args.generate()
        brgs.generate()
        poke(INSTRUCTION_RANDIT)
    }
}