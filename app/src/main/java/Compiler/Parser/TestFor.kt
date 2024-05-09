package Compiler.Parser

import Compiler.INSTRUCTION_CHOSE
import Compiler.adr

class TestFor (nl:Syntax, nr:Syntax, inst: Int = 0): BinaryOperation(nl,nr){
    var instr = inst
    override fun generate() {
        l.generate()
        r.generate()
        if(instr != 0){
            poke(instr)
        }else {
            poke(INSTRUCTION_CHOSE)
        }
    }
    fun generate2() {
        l.generate()
        r.generate()
        if(instr != 0){
            poke(instr)
        }else {
            adr += 1
        }
    }
}