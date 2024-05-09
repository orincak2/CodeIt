package Compiler.Parser

import Compiler.INSTRUCTION_ADD
import Compiler.INSTRUCTION_JUMP
import Compiler.INSTRUCTION_JUMPIFFALSE
import Compiler.INSTRUCTION_PUSH
import Compiler.adr
import Compiler.mem

class ForLoop(val start: Assign, val test: Syntax, val body: Syntax, val element: Assign?, val list: Assign?) : Syntax() {
    override fun generate() {
        start.generate()
        if(list != null){
            list.generate()
        }
        var test_adr = adr
        test.generate()
        poke(INSTRUCTION_JUMPIFFALSE)
        var jump_ins = adr
        adr += 1
        if(element != null){
            element.generate()
        }
        body.generate()
        start.varr!!.generate()
        poke(INSTRUCTION_PUSH)
        poke(1)
        poke(INSTRUCTION_ADD)
        (start.varr as Variable).generate_set()


        poke(INSTRUCTION_JUMP)
        poke(test_adr)
        //poke(INSTRUCTION_RETURN)
        //poke(1)
        mem[jump_ins] = adr.toFloat()
    }
}