package Compiler.Parser

import Compiler.INSTRUCTION_JUMP
import Compiler.INSTRUCTION_JUMPIFFALSE
import Compiler.adr
import Compiler.mem

class While(ntest: Syntax, nbody: Syntax ):Syntax(){
    var test = ntest
    var body = nbody
    override fun generate() {
        var test_adr = adr
        test.generate()
        poke(INSTRUCTION_JUMPIFFALSE)
        var jump_ins = adr
        adr += 1
        body.generate()
        poke(INSTRUCTION_JUMP)
        poke(test_adr)
        mem[jump_ins] = adr.toFloat()
    }
}