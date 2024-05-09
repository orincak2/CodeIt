package Compiler.Parser

import Compiler.INSTRUCTION_LOOP
import Compiler.INSTRUCTION_SET
import Compiler.adr
import Compiler.counter_adr

class Repeat(nCount : Syntax,nBody: Block):Syntax(){
    var count = nCount
    var body = nBody

    override fun generate() {
        count.generate()
        poke(INSTRUCTION_SET)
        poke(counter_adr)

        counter_adr -= 1
        var loop_body = adr
        body.generate()
        counter_adr +=1
        poke(INSTRUCTION_LOOP)
        poke(counter_adr)
        poke(loop_body)
    }
}