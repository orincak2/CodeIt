package Compiler.Parser

import Compiler.INSTRUCTION_GET
import Compiler.INSTRUCTION_SET

class GlobalVariable(nname:String, nPos: Float):Variable(nname,nPos){

    override fun generate() {
        poke(INSTRUCTION_GET)
        poke(pos)
    }
    override fun generate_set(){
        poke(INSTRUCTION_SET)
        poke(pos)
    }
}