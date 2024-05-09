package Compiler.Parser

import Compiler.INSTRUCTION_GETLOCAL
import Compiler.INSTRUCTION_SETLOCAL

class LocalVariable(nname:String, nPos: Float):Variable(nname,nPos){

    override fun generate() {
        poke(INSTRUCTION_GETLOCAL)
        poke(pos)
    }
    override fun generate_set(){
        poke(INSTRUCTION_SETLOCAL)
        poke(pos)
    }
}